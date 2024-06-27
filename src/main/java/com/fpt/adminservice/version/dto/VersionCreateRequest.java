package com.fpt.adminservice.version.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionCreateRequest {
    private String version;
    private String script;
}
