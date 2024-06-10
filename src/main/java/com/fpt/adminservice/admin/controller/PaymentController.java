package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.GenerateQRRequest;
import com.fpt.adminservice.admin.dto.PaymentCasso;
import com.fpt.adminservice.admin.dto.PaymentWebHook;
import com.fpt.adminservice.admin.service.PaymentService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/generateQR")
    public ResponseEntity<BaseResponse> generateQR(@RequestBody GenerateQRRequest request) {
        return ResponseEntity.ok(paymentService.GenerateQR(request.getOrderId(), request.getAmount()));
    }

    @PostMapping("/handleWebhook")
    public ResponseEntity<BaseResponse> handlePaymentWebhook(
            @RequestHeader("secure-token") String secureToken,
            @RequestBody PaymentWebHook paymentWebHook) {
        return ResponseEntity.ok(paymentService.HandlePayment(secureToken, paymentWebHook.getData()));
    }
}
