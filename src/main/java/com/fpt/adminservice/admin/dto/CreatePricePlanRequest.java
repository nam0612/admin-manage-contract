package com.fpt.adminservice.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreatePricePlanRequest {
    private String name;
    private String description;
    private Double price;
    private Integer timeWithYears;
    private Integer discount;
}
