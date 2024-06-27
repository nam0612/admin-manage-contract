package com.fpt.adminservice.version.controller;

import com.fpt.adminservice.admin.repository.TenantDbListRepository;
import com.fpt.adminservice.utils.BaseResponse;
import com.fpt.adminservice.version.config.MultitenantConfiguration;
import com.fpt.adminservice.version.dto.VersionCreateRequest;
import com.fpt.adminservice.version.model.TenantContext;
import com.fpt.adminservice.version.service.VersionManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequiredArgsConstructor
@RequestMapping("/version")
public class UpdateAppController {

    private final VersionManagerService versionManagerService;

    @PostMapping("/public/update-app")
    public ResponseEntity<BaseResponse> updateApp(
            @RequestHeader("X-TenantID") String tenantId,
            @RequestParam String version) {
        TenantContext.setCurrentTenant(tenantId);
        BaseResponse response = versionManagerService.updateApp(tenantId, version);
        TenantContext.clear();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        BaseResponse response = versionManagerService.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(
            @RequestBody VersionCreateRequest versionCreateRequest
    ) {
        return ResponseEntity.ok(versionManagerService.create(versionCreateRequest.getVersion(), versionCreateRequest.getScript()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable String id) {
        return ResponseEntity.ok(versionManagerService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable String id,
                                               @RequestBody VersionCreateRequest versionCreateRequest
    ) {
        return ResponseEntity.ok(versionManagerService.update(id, versionCreateRequest));
    }
}

