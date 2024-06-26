package com.fpt.adminservice.admin.dto;

import com.fpt.adminservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private String id;
    private String companyName;
    private String taxCode;
    private String presenter;
    private String phone;
    private String email;
    private UserStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime startDateUseService;
    private LocalDateTime endDateUseService;
    private LocalDateTime registerDate;
    private Double price;
    private String pricePlanId;
    private String pricePlanName;
    private String avatar;
    private String file;
}
