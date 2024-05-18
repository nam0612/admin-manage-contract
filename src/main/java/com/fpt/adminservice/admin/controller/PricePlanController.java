package com.fpt.adminservice.admin.controller;

import com.fpt.adminservice.admin.dto.CreatePricePlanRequest;
import com.fpt.adminservice.admin.dto.PricePlanDto;
import com.fpt.adminservice.admin.service.PricePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manager/pricePlan")
@RequiredArgsConstructor
public class PricePlanController {

    private final PricePlanService pricePlanService;

    @GetMapping
    public ResponseEntity<List<PricePlanDto>> getAll() {
        return ResponseEntity.ok(pricePlanService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(pricePlanService.delete(id));
    }

    @PostMapping
    public ResponseEntity<PricePlanDto> create(@RequestBody CreatePricePlanRequest pricePlanDto) {

        PricePlanDto dto = pricePlanService.create(pricePlanDto);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PricePlanDto> update(@RequestBody PricePlanDto pricePlanDto, @PathVariable String id) {
        return ResponseEntity.ok(pricePlanService.update(pricePlanDto, id));
    }
}
