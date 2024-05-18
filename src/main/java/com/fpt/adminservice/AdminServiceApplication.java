package com.fpt.adminservice;

import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.model.UserStatus;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.pricePlan.model.PlanStatus;
import com.fpt.adminservice.pricePlan.model.PricePlan;
import com.fpt.adminservice.pricePlan.repository.PricePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@SpringBootApplication
@RequiredArgsConstructor
public class AdminServiceApplication {
    private final UserRepository userRepository;
    private final PricePlanRepository pricePlanRepository;

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

        if (pricePlanRepository.count() == 0) {
            PricePlan pricePlan3 = new PricePlan();
            pricePlan3.setName("3 Months");
            pricePlan3.setPrice(2500000.0);
            pricePlan3.setStatus(PlanStatus.ACTIVE);
            pricePlan3.setCreatedDate(LocalDateTime.now());
            pricePlanRepository.save(pricePlan3);

            PricePlan pricePlan6 = new PricePlan();
            pricePlan6.setName("6 Months");
            pricePlan6.setPrice(5000000.0);
            pricePlan6.setStatus(PlanStatus.ACTIVE);
            pricePlan6.setCreatedDate(LocalDateTime.now());
            pricePlanRepository.save(pricePlan6);

            PricePlan pricePlan12 = new PricePlan();
            pricePlan12.setName("12 Months");
            pricePlan12.setPrice(7500000.0);
            pricePlan12.setStatus(PlanStatus.ACTIVE);
            pricePlan12.setCreatedDate(LocalDateTime.now());
            pricePlanRepository.save(pricePlan12);
        }
    }
}
