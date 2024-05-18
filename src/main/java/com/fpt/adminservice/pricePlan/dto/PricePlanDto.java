package com.fpt.adminservice.pricePlan.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PricePlanDto {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
