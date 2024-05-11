package com.fpt.adminservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserCreateRequest {
    private String companyName;
    private String taxCode;
    private String presenter;
    private String phone;
    private String email;

}
