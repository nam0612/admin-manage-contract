package com.fpt.adminservice.admin.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PricePlanDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer timeWithYears;
    private Integer discount;
    private String createdDate;
    private String updatedDate;
}
