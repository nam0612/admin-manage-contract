package com.fpt.adminservice.version.service;

import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.version.dto.VersionCreateRequest;
import com.fpt.adminservice.version.dto.VersionResponse;

public interface VersionManagerService {
    BaseResponse updateApp(String tenantId, String version);
    BaseResponse create(String version, String script);
    BaseResponse delete(String versionId);
    BaseResponse getAll();
    BaseResponse update(String id, VersionCreateRequest request);
}
