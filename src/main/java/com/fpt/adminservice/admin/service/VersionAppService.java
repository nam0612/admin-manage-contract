package com.fpt.adminservice.admin.service;

import com.fpt.adminservice.utils.BaseResponse;

public interface VersionAppService {
    public BaseResponse updateVersion(String userId, String version);
}
