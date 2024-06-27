package com.fpt.adminservice.version.service.impl;

import com.fpt.adminservice.admin.model.TenantDbList;
import com.fpt.adminservice.admin.repository.TenantDbListRepository;
import com.fpt.adminservice.admin.service.UserService;
import com.fpt.adminservice.auth.repository.UserRepository;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.utils.Constants;
import com.fpt.adminservice.version.config.MultitenantConfiguration;
import com.fpt.adminservice.version.dto.VersionCreateRequest;
import com.fpt.adminservice.version.model.Version;
import com.fpt.adminservice.version.model.VersionManager;
import com.fpt.adminservice.version.repository.VersionRepository;
import com.fpt.adminservice.version.service.VersionManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionManagerServiceImpl implements VersionManagerService {

    private final MultitenantConfiguration multitenantConfiguration;
    private final VersionRepository versionRepository;
    private final TenantDbListRepository tenantDbListRepository;

    @Override
    public BaseResponse updateApp(String tenantId, String version) {
        DataSource dataSource = multitenantConfiguration.dataSource(tenantId);
        Optional<Version> optionalVersion = versionRepository.findByVersion(version);
        Optional<TenantDbList> optionalTenantDbList = tenantDbListRepository.findByTenantId(tenantId);

        if (optionalTenantDbList.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "tenant database is invalid", true, null);
        }
        if (optionalVersion.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "version app is invalid", true, null);
        }
        if (dataSource == null) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "tenant is invalid", true, null);
        }


        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = optionalVersion.get().getScript();
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "update fail", true, null);
        }

        optionalTenantDbList.get().setVersion(version);
        tenantDbListRepository.save(optionalTenantDbList.get());
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "update successfully version: " + version, true, null);
    }

    public BaseResponse create(String version, String script) {
        try {
            Version newVersion = Version.builder()
                    .version(version)
                    .script(script)
                    .build();
            versionRepository.save(newVersion);
        } catch (Exception e) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "create fail", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "create successfully", true, version);
    }

    public BaseResponse delete(String versionId) {
        Optional<Version> version = versionRepository.findById(versionId);
        if (version.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "version not exist", true, null);
        }
        try {
            version.get().setStatus(Constants.Status.INACTIVE);
            versionRepository.save(version.get());
        } catch (Exception e) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "create fail", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "create successfully", true, version);
    }

    public BaseResponse getAll() {
        List<Version> versions = versionRepository.findAll();
        if (versions.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "version not exist", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "version list successfully", true, versions);
    }

    @Override
    public BaseResponse update(String id, VersionCreateRequest request) {
        Optional<Version> optionalVersion = versionRepository.findById(id);
        if (optionalVersion.isEmpty()) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "version not exist", true, null);
        }
        optionalVersion.get().setScript(request.getScript() == null ? "" : request.getScript());
        optionalVersion.get().setScript(request.getVersion() == null ? "" : request.getVersion());

        try {

            versionRepository.save(optionalVersion.get());
        } catch (Exception e) {
            return new BaseResponse(Constants.ResponseCode.SUCCESS, "update fail", true, null);
        }
        return new BaseResponse(Constants.ResponseCode.SUCCESS, "update successfully", true, optionalVersion.get());
    }
}
