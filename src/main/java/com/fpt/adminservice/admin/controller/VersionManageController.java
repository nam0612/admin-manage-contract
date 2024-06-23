package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.service.VersionAppService;
import com.fpt.adminservice.config.TenantContext;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public/update-version")
@RequiredArgsConstructor
public class VersionManageController {

    private final VersionAppService versionAppService;

    @GetMapping
    public BaseResponse updateVersion(
            @RequestHeader("tenantId") String tenantId,
            @RequestParam("version") String version)
    {
        TenantContext.setCurrentTenant(tenantId);
        versionAppService.updateVersion(tenantId, version);
        TenantContext.clear();
        return null;
    }
}
