package com.fpt.adminservice.admin.service.impl;


import com.fpt.adminservice.admin.dto.PaymentCasso;
import com.fpt.adminservice.admin.dto.QRDataDto;
import com.fpt.adminservice.admin.dto.RequestQrDto;
import com.fpt.adminservice.admin.service.PaymentService;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BaseResponse FindById(String id) {
        return null;
    }

    @Override
    public BaseResponse HandlePayment(List<PaymentCasso> paymentCasso) {
        if (paymentCasso == null) { return null; }

//        List<PaymentDto> paymentDTOs = new List<PaymentDto>();

        List<Object[]> errorPayment = new ArrayList<>();
        for (PaymentCasso payment : paymentCasso) {
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
                if (destrip[j].contains("ECOMMERCE")) {
                    indexIdOrder = j;
                }
            }
            String OrderId = destrip[indexIdOrder + 1];

//            OrderDto order = _orderService.GetOrder(OrderId);
//            Payments payments = _paymentRepository.FindByOrderId(OrderId);
//            if (payments != null && order != null)
//            {
//                if (payment.Amount == order.OrderTotal)
//                {
//                    payments.paymentStatus = PaymentStatus.COMPLETED;
//                }
//                else if (payment.Amount < order.OrderTotal)
//                {
//                    payments.refund = payment.Amount;
//                    payments.paymentStatus = PaymentStatus.REFUND;
//                }
//                else if (payment.Amount > order.OrderTotal)
//                {
//                    decimal refund = payment.Amount - order.OrderTotal;
//                    payments.refund = refund;
//                    payments.paymentStatus = PaymentStatus.COMPLETED;
//                }
//                _paymentRepository.Update(payments);
//                paymentDTOs.Add(new PaymentDto
//                {
//                    id = payments.id,
//                            orderId = payments.orderId,
//                            paymentStatus = payments.paymentStatus,
//                            refund = payments.refund,
//                });
//                _paymentRepository.Save();
//            }
//        }
        }
        return new BaseResponse(Constants.ResponseCode.FAILURE, "Upload Contract Failed", true, null);
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
                    .addInfo("ECOMMERCE " + orderId)
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
