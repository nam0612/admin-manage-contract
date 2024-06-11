package com.fpt.adminservice.admin.service.impl;

import com.fpt.adminservice.admin.dto.QueueExtendCreate;
import com.fpt.adminservice.admin.dto.QueueExtendDto;
import com.fpt.adminservice.admin.model.QueueExtend;
import com.fpt.adminservice.admin.repository.PricePlanRepository;
import com.fpt.adminservice.admin.repository.QueueExtendRepository;
import com.fpt.adminservice.admin.service.QueueExtendService;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.enums.QueueExtendStatus;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueExtendServiceImpl implements QueueExtendService {

    private final QueueExtendRepository queueExtendRepository;
    private final PricePlanRepository pricePlanRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public BaseResponse getAll(Pageable pageable, String status) {
        var queueExtends = queueExtendRepository.getAll(status, pageable);

        if (queueExtends.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Not have any request extend price plan", true, null);
        }

        List<QueueExtendDto> queueExtendList = queueExtends.get().map(
                item -> QueueExtendDto.builder()
                        .id(item.getId())
                        .companyName(item.getCompanyName())
                        .taxCode(item.getTaxCode())
                        .pricePlanId(item.getPricePlanId())
                        .pricePlanName(item.getPricePlanName())
                        .companyId(item.getCompanyId())
                        .status(item.getStatus().getQueueExtendStatus())
                        .createdDate(String.valueOf(item.getCreatedDate()))
                        .updatedDate(String.valueOf(item.getUpdatedDate()))
                        .build()
                )
                .toList();
        Page<QueueExtendDto> pageObject = new PageImpl<>(queueExtendList, pageable, queueExtends.getTotalElements());

        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Search Successfully", true, pageObject);
    }

    @Override
    public BaseResponse approve(String userId, String pricePlanId, boolean isPayed) {
        var queueExtend = queueExtendRepository.findByCompanyIdAndAndPricePlanId(userId, pricePlanId);

        if (queueExtend.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Request extend not exist", true, null);
        }

        if(!isPayed) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Payment need complete before extend", true, null);
        }

        var userDto = userService.extendService(userId, pricePlanId);
        if (userDto == null) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Request extend not valid", true, userDto);
        }
        queueExtend.get().setStatus(QueueExtendStatus.APPROVED);
        queueExtendRepository.save(queueExtend.get());
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Approve Successfully", true, null);
    }

    @Override
    public BaseResponse create(QueueExtendCreate queueExtendCreate) {
        QueueExtend queueExtend = new QueueExtend();
        var company = userRepository.findById(queueExtendCreate.getCompanyId());
        if (company.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Company not found", true, null);
        }
        queueExtend.setCompanyId(company.get().getId());
        queueExtend.setCompanyName(company.get().getCompanyName());


        var pricePlan = pricePlanRepository.findById(queueExtendCreate.getPricePlanId());
        if (pricePlan.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Price plan not found", true, null);
        }

        queueExtend.setPricePlanId(pricePlan.get().getId());
        queueExtend.setPricePlanName(pricePlan.get().getName());
        queueExtend.setPrice(pricePlan.get().getPrice());


        queueExtend.setTaxCode(company.get().getTaxCode());
        queueExtend.setCreatedDate(LocalDateTime.now());
        queueExtend.setStatus(QueueExtendStatus.PROCESSING);

        queueExtendRepository.save(queueExtend);
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Create Successfully", true, QueueExtendDto.builder()
                .id(queueExtend.getId())
                .amout(queueExtend.getPrice())
                .build());
    }

}
