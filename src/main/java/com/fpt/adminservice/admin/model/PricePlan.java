package com.fpt.adminservice.admin.model;

import com.fpt.adminservice.enums.PlanStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_plan")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricePlan {
    @Id
    @UuidGenerator
    private String id;

    private String name;
    private String description;
    private Integer timeWithYears;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    private Integer discount;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
