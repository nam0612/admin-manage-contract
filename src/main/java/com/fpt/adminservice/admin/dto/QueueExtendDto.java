package com.fpt.adminservice.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class QueueExtendDto {
    private String id;
    private String companyId;
    private String companyName;
    private String taxCode;
    private String pricePlanId;
    private String pricePlanName;
    private String status;
    private String createdDate;
    private String updatedDate;
    private Double amout;
}
