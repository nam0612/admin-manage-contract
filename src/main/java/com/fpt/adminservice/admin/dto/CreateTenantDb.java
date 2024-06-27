package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTenantDb {
    private String username;
    private String password;
    private String url;
    private String tenantId;
    private String version;
}
