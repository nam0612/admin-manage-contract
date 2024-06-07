package com.fpt.adminservice.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QRDataDto {
    private String Code;
    private String Desc;
    private DataDto Data;
}
