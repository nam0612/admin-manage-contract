package com.fpt.adminservice.config;

import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.auth.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fpt.adminservice.utils.Constants.MESSAGE_MAIL.EXPIRED_PACKAGE;

@Component
@RequiredArgsConstructor
public class ScheduledTasksConfig {

    private final MailService mailService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 10 15 * * ?")
    public void scheduleTaskUsingCronExpression() throws MessagingException {
        List<UserInterface> userInterfaceList = userRepository.getUserHaveCloseEndUseService();
        String[] emailListTo = userInterfaceList.stream().map(m -> m.getEmail()).toList().toArray(new String[0]);
        System.out.println(emailListTo[0]);
        mailService.sendNewMail(emailListTo, null,"" , EXPIRED_PACKAGE, null);
    }
}
