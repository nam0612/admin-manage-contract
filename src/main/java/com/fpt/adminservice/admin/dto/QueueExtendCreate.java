package com.fpt.adminservice.admin.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QueueExtendCreate {
    private String companyId;
    private String pricePlanId;
    private boolean isPayed;
}
