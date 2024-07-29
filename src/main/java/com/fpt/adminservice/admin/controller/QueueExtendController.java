package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.QueueExtendCreate;
import com.fpt.adminservice.admin.model.QueueExtend;
import com.fpt.adminservice.admin.service.QueueExtendService;
import com.fpt.adminservice.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
@RequestMapping("/manager/queueExtend")
@RequiredArgsConstructor
public class QueueExtendController {
    private final QueueExtendService queueExtendService;

    @GetMapping()
    public BaseResponse getAll(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "companyName", required = false) String name,
            @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            Pageable pageable) {
        return queueExtendService.getAll(pageable, status, name, fromDate, toDate);
    }

    @PostMapping("/public")
    public BaseResponse create(@RequestBody QueueExtendCreate queueExtendCreate){
        return queueExtendService.create(queueExtendCreate);
    }

    @PutMapping
    public BaseResponse approve(@RequestBody QueueExtendCreate queueExtend){
        return queueExtendService.approve(queueExtend.getCompanyId(), queueExtend.getPricePlanId());
    }

    @GetMapping("/public/{page}/{size}/{companyId}")
    public BaseResponse getByCompanyId(@PathVariable(name = "companyId") String id,
        @PathVariable("page") int page, @PathVariable("size") int size
    ){
        return queueExtendService.getByCompanyId(id, Pageable.ofSize(size).withPage(page));
    }

}
