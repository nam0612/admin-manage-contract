package com.fpt.adminservice.pricePlan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

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
    private double price;

    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    private LocalDateTime CreatedDate;
    private LocalDateTime UpdatedDate;
}
