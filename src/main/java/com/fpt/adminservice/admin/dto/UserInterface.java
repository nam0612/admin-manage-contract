package com.fpt.adminservice.admin.dto;

import com.fpt.adminservice.admin.model.UserStatus;

import java.time.LocalDateTime;

public interface UserInterface {
    String getId();
    String getCompanyName();
    UserStatus getStatus();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
    LocalDateTime getStartDateUseService();
    LocalDateTime getEndDateUseService();
    LocalDateTime getRegisterDate();
    Double getPrice();
    String getTaxCode();
    String getPlanId();
    String getPlanName();
}
