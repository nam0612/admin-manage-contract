package com.fpt.adminservice.admin.service.impl;

import com.fpt.adminservice.admin.dto.QueueExtendCreate;
import com.fpt.adminservice.admin.dto.QueueExtendDto;
import com.fpt.adminservice.admin.model.PricePlan;
import com.fpt.adminservice.admin.model.QueueExtend;
import com.fpt.adminservice.admin.repository.PricePlanRepository;
import com.fpt.adminservice.admin.repository.QueueExtendRepository;
import com.fpt.adminservice.admin.service.QueueExtendService;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.enums.PlanStatus;
import com.fpt.adminservice.enums.QueueExtendStatus;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QueueExtendServiceImpl implements QueueExtendService {

    private final QueueExtendRepository queueExtendRepository;
    private final PricePlanRepository pricePlanRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public BaseResponse getAll(Pageable pageable, String status, String name, LocalDate fromDate, LocalDate toDate) {
        var queueExtends = queueExtendRepository.getAll(QueryUtils.appendPercent(status), QueryUtils.appendPercent(name), fromDate, toDate, pageable);

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
                        .amout(item.getPrice())
                        .build()
                )
                .toList();
        Page<QueueExtendDto> pageObject = new PageImpl<>(queueExtendList, pageable, queueExtends.getTotalElements());

        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Search Successfully", true, pageObject);
    }

    @Override
    public BaseResponse approve(String userId, String pricePlanId) {
        var queueExtend = queueExtendRepository.findByCompanyIdAndAndPricePlanId(userId, pricePlanId, QueueExtendStatus.PROCESSING.getQueueExtendStatus());

        if (queueExtend.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Request extend not exist", true, null);
        }

        var userDto = userService.extendService(userId, pricePlanId).getObject();
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
        var queueExtendExist = queueExtendRepository.findByCompanyIdAndStatus(company.get().getId(), QueueExtendStatus.PROCESSING.getQueueExtendStatus());
        if(queueExtendExist.isPresent()) {
            return new BaseResponse(Constants.ResponseCode.FAILURE, "Extend Request existed, You need wait to approve", false, null);
        }

        var pricePlan = pricePlanRepository.findByIdAndStatus(queueExtendCreate.getPricePlanId(), PlanStatus.ACTIVE);
        if (pricePlan.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Price plan not found", true, null);
        }

        queueExtend.setPricePlanId(pricePlan.get().getId());
        queueExtend.setPricePlanName(pricePlan.get().getName());
        queueExtend.setPrice(pricePlan.get().getPrice() - pricePlan.get().getPrice() / 100 * pricePlan.get().getDiscount());


        queueExtend.setTaxCode(company.get().getTaxCode());
        queueExtend.setCreatedDate(LocalDateTime.now());
        queueExtend.setStatus(QueueExtendStatus.PROCESSING);

        QueueExtend newQueueExtend = queueExtendRepository.save(queueExtend);
        String orderNumber = Arrays.toString(newQueueExtend.getId().split("-"));
        newQueueExtend.setOrderNumber(orderNumber);
        queueExtendRepository.save(newQueueExtend);
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Create Successfully", true, QueueExtendDto.builder()
                .id(queueExtend.getId())
                .amout(queueExtend.getPrice())
                .build());
    }

    @Override
    public BaseResponse getByCompanyId(String companyId, Pageable pageable) {
        var queuExtendList = queueExtendRepository.findByEmail(companyId, pageable);

        if(queuExtendList.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "No extend plan", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Search Successfully", true, queuExtendList);
    }

    @Override
    public BaseResponse reject(String id) {
        var queueExtend = queueExtendRepository.findById(id);

        if (queueExtend.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Request extend not exist", true, null);
        }
        queueExtend.get().setStatus(QueueExtendStatus.REJECTED);
        queueExtendRepository.save(queueExtend.get());
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Reject Successfully", true, null);
    }

}
