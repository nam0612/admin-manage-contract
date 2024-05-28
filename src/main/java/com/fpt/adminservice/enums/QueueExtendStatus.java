package com.fpt.adminservice.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum QueueExtendStatus {
    PROCESSING("PROCESSING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    ;

    private final String queueExtendStatus;

    QueueExtendStatus(String userStatus) {
        this.queueExtendStatus = userStatus;
    }
}
