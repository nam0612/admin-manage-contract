package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailCodeRequest {
    private String email;
    private Integer code;
}
