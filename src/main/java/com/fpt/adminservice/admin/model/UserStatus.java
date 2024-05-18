package com.fpt.adminservice.admin.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum UserStatus {
    PROCESSING("PROCESSING"),
    LOCKED("LOCKED"),
    EXPIRED("EXPIRED"),
    INUSE("INUSE");
    ;

    private final String userStatus;

    UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
