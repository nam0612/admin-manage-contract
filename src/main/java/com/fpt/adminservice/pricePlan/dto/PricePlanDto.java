package com.fpt.adminservice.pricePlan.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PricePlanDto {
    private String id;
    private String name;
    private String description;
    private double price;
}
