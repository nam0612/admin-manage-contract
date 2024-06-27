package com.fpt.adminservice.admin.service.impl;

import com.fpt.adminservice.admin.dto.CreateTenantDb;
import com.fpt.adminservice.admin.model.TenantDbList;
import com.fpt.adminservice.admin.repository.TenantDbListRepository;
import com.fpt.adminservice.admin.service.TenantDbManagerService;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.version.model.Version;
import com.fpt.adminservice.version.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantDbManagerServiceImpl implements TenantDbManagerService {

    private final TenantDbListRepository tenantDbListRepository;
    private final UserRepository userRepository;
    private final VersionRepository versionRepository;


    @Override
    public BaseResponse create(CreateTenantDb createTenantDb) {
        Optional<User> userOptional = userRepository.findById(createTenantDb.getTenantId());
        Optional<Version> versionOptional = versionRepository.findByVersion(createTenantDb.getVersion());
        if(userOptional.isEmpty()){
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "User not exist", true, null);
        }

        if(versionOptional.isEmpty()){
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Version not exist", true, null);
        }
        TenantDbList createDb = TenantDbList.builder()
                .createdDate(LocalDateTime.now())
                .url(createTenantDb.getUrl())
                .username(createTenantDb.getUsername())
                .status(Constants.Status.ACTIVE)
                .password(createTenantDb.getPassword())
                .tenantId(createTenantDb.getTenantId())
                .version(createTenantDb.getVersion())
                .build();
        try {
            tenantDbListRepository.save(createDb);
        } catch (Exception ex) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "Create db failed", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "Create db success", true, null);
    }

    @Override
    public BaseResponse delete() {
        return null;
    }

    @Override
    public BaseResponse update() {
        return null;
    }

    @Override
    public BaseResponse get() {
        return null;
    }
}
