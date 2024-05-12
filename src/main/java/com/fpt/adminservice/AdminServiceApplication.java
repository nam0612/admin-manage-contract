package com.fpt.adminservice;

import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.model.UserStatus;
import com.fpt.adminservice.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@RequiredArgsConstructor
public class AdminServiceApplication {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPhone("0352334588");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(adminUser);
            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPhone("123456789");
            user.setPassword(passwordEncoder.encode("user"));
            user.setStatus(UserStatus.PROCESSING);
            userRepository.save(user);
        }
    }
}
