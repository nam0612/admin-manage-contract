package com.fpt.adminservice.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DataDto {
    private String qrCode;
    private String qrDataURL;
}
