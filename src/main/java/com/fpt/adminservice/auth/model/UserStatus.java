package com.fpt.adminservice.auth.model;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum UserStatus {
    ACTIVE("ACTIVE"),
    PROCESSING("PROCESSING"),
    INACTIVE("INACTIVE"),
    ;

    private final String userStatus;

    UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
