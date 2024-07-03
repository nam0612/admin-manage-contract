package com.fpt.adminservice.config;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.fpt.adminservice.utils.Constants.MESSAGE_MAIL.EXPIRED_PACKAGE;

@Component
public class ScheduledTasksConfig {
    @Autowired
    private MailService mailService;
    @Scheduled(cron = "0 10 15 * * ?")
    public void scheduleTaskUsingCronExpression() throws MessagingException {
        // function get email và ngày hết hạn dịch vụ check còn 7 ngày thì gửi mail cảnh báo
        String[] emailListTo = new String[]{"tuddaallt@gmail.com"};
        mailService.sendNewMail(emailListTo, null,"" , EXPIRED_PACKAGE, null);
    }
}
