package com.fpt.adminservice.admin.service;


import com.fpt.adminservice.admin.dto.PaymentCasso;
import com.fpt.adminservice.utils.BaseResponse;

import java.util.List;

public interface PaymentService {
//    BaseResponse UpsertPayment(PaymentDto paymentDto);
    BaseResponse FindById(String id);
    BaseResponse HandlePayment(String secureToken, List<PaymentCasso> payment);
    BaseResponse GetPayments();
    BaseResponse GenerateQR(String orderId, float amount);
}
