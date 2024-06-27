package com.fpt.adminservice.admin.service;

import com.fpt.adminservice.admin.dto.CreateTenantDb;
import com.fpt.adminservice.utils.BaseResponse;

public interface TenantDbManagerService {
    BaseResponse create(CreateTenantDb createTenantDb);
    BaseResponse delete();
    BaseResponse update();
    BaseResponse get();
}
