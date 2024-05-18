package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.config.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  private final MailService mailService;

  @PostMapping("/send-mail")
  public String sendMail(@RequestParam String[] to,
      @RequestParam(required = false) String[] cc,
      @RequestParam String subject,
      @RequestParam String htmlContent,
      @RequestParam(required = false) MultipartFile[] attachments) throws MessagingException {
    mailService.sendNewMail(to, cc, subject, htmlContent ,attachments);
    return "SEND OK";
  }
}
