package com.fpt.adminservice.auth.dto;

import com.fpt.adminservice.auth.model.UserStatus;

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
    String getPricePlan();
    String getTaxCode();
    String getPlanId();
}
