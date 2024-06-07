package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateQRRequest {
    private String orderId;
    private float amount;
}
