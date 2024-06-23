package com.fpt.adminservice.admin.service.impl;

import com.fpt.adminservice.admin.repository.VersionRepository;
import com.fpt.adminservice.admin.service.VersionAppService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionAppServiceImpl implements VersionAppService {

    private final VersionRepository versionRepository;

    @Override
    public BaseResponse updateVersion(String userId, String version) {
        String sqlScript = versionRepository.getScriptByVersion(version);

        return null;
    }
}
