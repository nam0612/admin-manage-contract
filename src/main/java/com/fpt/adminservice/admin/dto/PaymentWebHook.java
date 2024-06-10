package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentWebHook {
    private int error;
    private List<PaymentCasso> data;
}
