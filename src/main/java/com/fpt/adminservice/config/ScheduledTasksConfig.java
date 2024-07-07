package com.fpt.adminservice.config;

import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.auth.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.fpt.adminservice.utils.Constants.MESSAGE_MAIL.EXPIRED_PACKAGE;

@Component
@RequiredArgsConstructor
public class ScheduledTasksConfig {
    private final MailService mailService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 15 * * ?")
    public void scheduleTaskUsingCronExpression() throws MessagingException {
        // function get email và ngày hết hạn dịch vụ check còn 7 ngày thì gửi mail cảnh báo
        List<UserInterface> users = userRepository.getEndUserServiceLessTime();
        String[] emailListTo = users.stream().map(e -> e.getEmail()).toArray(String[]::new);
        mailService.sendNewMail(emailListTo, null,"" , EXPIRED_PACKAGE, null);
    }
}
