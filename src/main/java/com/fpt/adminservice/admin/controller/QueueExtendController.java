package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.QueueExtendCreate;
import com.fpt.adminservice.admin.model.QueueExtend;
import com.fpt.adminservice.admin.service.QueueExtendService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/manager/queueExtend")
@RequiredArgsConstructor
public class QueueExtendController {
    private final QueueExtendService queueExtendService;

    @GetMapping()
    public BaseResponse getAll(
            @RequestParam(name = "status", required = false) String status,
            Pageable pageable) {
        return queueExtendService.getAll(pageable, status);
    }

    @PostMapping()
    public BaseResponse create(@RequestBody QueueExtendCreate queueExtendCreate){
        return queueExtendService.create(queueExtendCreate);
    }

    @PutMapping
    public BaseResponse approve(@RequestBody QueueExtendCreate queueExtend){
        return queueExtendService.approve(queueExtend.getCompanyId(), queueExtend.getPricePlanId());
    }

}
