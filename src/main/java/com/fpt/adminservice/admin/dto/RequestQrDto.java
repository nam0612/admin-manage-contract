package com.fpt.adminservice.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestQrDto {
    private String accountNo;
    private String accountName;
    private String acqId;
    private String addInfo;
    private float amount;
    private String template;
}
