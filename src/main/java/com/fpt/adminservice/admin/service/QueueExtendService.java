package com.fpt.adminservice.admin.service;

import com.fpt.adminservice.admin.dto.QueueExtendCreate;
import com.fpt.adminservice.utils.BaseResponse;
import org.springframework.data.domain.Pageable;

public interface QueueExtendService {
    BaseResponse getAll(Pageable pageable, String status);
    BaseResponse approve(String userId, String pricePlanId);
    BaseResponse create(QueueExtendCreate queueExtendCreate);
    BaseResponse getByCompanyId(String companyId, Pageable pageable);
}
