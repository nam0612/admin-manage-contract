package com.fpt.adminservice.auth.dto;

import com.fpt.adminservice.auth.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private String companyName;
    private String taxCode;
    private String presenter;
    private String phone;
    private String email;
    private UserStatus status;
}
