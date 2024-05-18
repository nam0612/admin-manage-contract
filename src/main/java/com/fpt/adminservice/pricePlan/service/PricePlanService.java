package com.fpt.adminservice.pricePlan.service;

import com.fpt.adminservice.pricePlan.dto.CreatePricePlanRequest;
import com.fpt.adminservice.pricePlan.dto.PricePlanDto;
import com.fpt.adminservice.pricePlan.model.PlanStatus;
import com.fpt.adminservice.pricePlan.model.PricePlan;
import com.fpt.adminservice.pricePlan.repository.PricePlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricePlanService {
    private final PricePlanRepository  pricePlanRepository;


    public List<PricePlanDto> getAll() {
        List<PricePlan> pricePlans = pricePlanRepository.findByStatus(PlanStatus.ACTIVE).orElseThrow();
        return  pricePlans.stream().map(item -> PricePlanDto.builder()
                .price(item.getPrice())
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .build()).toList();
    }

    @Transactional(rollbackOn = Exception.class)
    public PricePlanDto update(PricePlanDto dto, String id) {
        PricePlan pricePlan = pricePlanRepository.findById(id).orElseThrow();
        pricePlan.setPrice(dto.getPrice());
        pricePlan.setDescription(dto.getDescription());
        pricePlan.setName(dto.getName());
        pricePlan.setUpdatedDate(LocalDateTime.now());
        pricePlanRepository.save(pricePlan);
        return PricePlanDto.builder()
                .name(pricePlan.getName())
                .id(pricePlan.getId())
                .description(pricePlan.getDescription())
                .price(pricePlan.getPrice())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    public String delete(String id) {
        PricePlan pricePlan = pricePlanRepository.findById(id).orElseThrow();
        pricePlan.setStatus(PlanStatus.INACTIVE);
        pricePlan.setUpdatedDate(LocalDateTime.now());
        pricePlanRepository.save(pricePlan);
        return "Successfully";
    }

    @Transactional(rollbackOn = Exception.class)
    public PricePlanDto create(CreatePricePlanRequest dto) {
        if(dto == null) {
            return null;
        }
        PricePlan pricePlan = new PricePlan();
        pricePlan.setName(dto.getName());
        pricePlan.setDescription(dto.getDescription());
        pricePlan.setPrice(dto.getPrice());
        pricePlan.setStatus(PlanStatus.ACTIVE);
        pricePlan.setCreatedDate(LocalDateTime.now());
        pricePlanRepository.save(pricePlan);
        return PricePlanDto.builder()
                .name(pricePlan.getName())
                .id(pricePlan.getId())
                .price(pricePlan.getPrice())
                .description(pricePlan.getDescription())
                .build();
    }
}
