package com.fpt.adminservice.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_plan")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private LocalDateTime CreatedDate;
    private LocalDateTime UpdatedDate;
}
