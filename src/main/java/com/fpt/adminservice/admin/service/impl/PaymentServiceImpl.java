package com.fpt.adminservice.admin.service.impl;


import com.fpt.adminservice.admin.dto.PaymentCasso;
import com.fpt.adminservice.admin.dto.QRDataDto;
import com.fpt.adminservice.admin.dto.RequestQrDto;
import com.fpt.adminservice.admin.model.QueueExtend;
import com.fpt.adminservice.admin.repository.QueueExtendRepository;
import com.fpt.adminservice.admin.service.PaymentService;
import com.fpt.adminservice.admin.service.QueueExtendService;
import com.fpt.adminservice.enums.PaymentStatus;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final String tokenSecure = "AK_CS.d79a3660609b11efa5dbff93fab61642.iDVgCVKjyy4My8sqF78mxZrQ6Nyf6uF3mimIllC8x7qctwlIwUt9fgiZkdODvujzepI8kHr4";
    private final RestTemplate restTemplate;
    private final QueueExtendRepository queueExtendRepository;
    private final QueueExtendService queueExtendService;

    @Override
    public BaseResponse FindById(String id) {
        return null;
    }

    @Override
    public BaseResponse HandlePayment(String secureToken, List<PaymentCasso> paymentCasso) {
        if(!secureToken.equals(tokenSecure)) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "Payment is invalid", true, null);
        }
        if (DataUtil.isListNullOrEmpty(paymentCasso)) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Payment is not existed", true, null);
        }

        List<Object[]> errorPayment = new ArrayList<>();
        for (PaymentCasso payment : paymentCasso) {
            var queueExtendObject = queueExtendRepository.findByPaymentId(payment.getId());
            if(queueExtendObject.isPresent()){
                if(queueExtendObject.get().getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
                    break;
                } else {
                    queueExtendObject.get().setPaymentId(payment.getId());
                }
            }
            if (DataUtil.isNullOrEmpty(payment.Description)) {
                Object[] error = new Object[3];
                error[0] = payment.Id;
                error[1] = payment.Amount;
                error[2] = "Payment invalid";
                errorPayment.add(error);
            }

            String[] destrip = payment.Description.split(" ");
            int indexIdOrder = -1;
            for (int j = 0; j < destrip.length; j++) {
                if (destrip[j].contains("TDOCMAN")) {
                    indexIdOrder = j;
                }
            }
            String orderId = destrip[indexIdOrder + 1];

            var queueExtendbject = queueExtendRepository.findByOrderNumber(orderId);

            if (queueExtendbject.isEmpty()) {
                return new BaseResponse(Constants.ResponseCode.FAILURE, "Payment is not exist", true, null);
            }

            QueueExtend queueExtend = queueExtendbject.get();

            if(queueExtend.getPrice().equals(payment.getAmount())) {
                queueExtend.setPaymentStatus(PaymentStatus.COMPLETED);
                queueExtendService.approve(queueExtend.getCompanyId(), queueExtend.getPricePlanId());
            } else if (queueExtend.getPrice() < payment.getAmount()) {
                queueExtend.setPaymentStatus(PaymentStatus.COMPLETED);
                queueExtend.setRefund(payment.getAmount() - queueExtend.getPrice());
                queueExtendService.approve(queueExtend.getCompanyId(), queueExtend.getPricePlanId());
            } else if (queueExtend.getPrice() > payment.getAmount()) {
                queueExtend.setPaymentStatus(PaymentStatus.IN_PROGRESS);
                queueExtend.setRefund(payment.getAmount());
            }

            queueExtendRepository.save(queueExtend);
        }
        return new BaseResponse(Constants.ResponseCode.FAILURE, "Approved", true, null);
    }

    public BaseResponse GenerateQR(String orderId, float amount)
    {
        String url = "https://api.vietqr.io/v2/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        try
        {
            var data = RequestQrDto.builder()
                    .accountNo("9909090111")
                    .accountName("NGUYEN HAI NAM")
                    .acqId("970422")
                    .addInfo("TDOCMAN " + orderId)
                    .amount(amount)
                    .template("print")
                    .build();
            HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

            ResponseEntity<QRDataDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    QRDataDto.class
            );

            if (response.getBody() == null)
            {
                return new BaseResponse(Constants.ResponseCode.FAILURE, "Generate QR Failed", true, null);
            }
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Generate QR Success", true, response.getBody());
        }
        catch (Exception ex)
        {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "Generate QR Failed", true, null);
        }
    }

    @Override
    public BaseResponse GetPayments() {
        return null;
    }

}
