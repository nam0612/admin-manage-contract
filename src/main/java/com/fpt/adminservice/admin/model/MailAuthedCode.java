package com.fpt.adminservice.admin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "mail_authen_code")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailAuthedCode {
    @Id
    @UuidGenerator
    private String id;

    private Integer code;

    @Column(unique = true)
    private String email;

    private LocalDateTime expiryTime;

    private LocalDateTime startTime;
}
