package com.fpt.adminservice.admin.model;

import com.fpt.adminservice.enums.PaymentStatus;
import com.fpt.adminservice.enums.QueueExtendStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "queue_extend")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueExtend {
    @Id
    @UuidGenerator
    private String id;

    private String companyId;

    private String companyName;

    private String taxCode;

    private String pricePlanId;

    private String pricePlanName;

    @Enumerated(EnumType.STRING)
    private QueueExtendStatus status;

    @Column(nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Date createdDate;

    private Date updatedDate;
}
