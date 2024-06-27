package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.CreateTenantDb;
import com.fpt.adminservice.admin.service.TenantDbManagerService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/manager/tenantDb")
@RequiredArgsConstructor
public class TenantDBController {
    private final TenantDbManagerService tenantDbManagerService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(
            @RequestBody CreateTenantDb createTenantDb
            ) {
        return ResponseEntity.ok(tenantDbManagerService.create(createTenantDb));
    }

}
