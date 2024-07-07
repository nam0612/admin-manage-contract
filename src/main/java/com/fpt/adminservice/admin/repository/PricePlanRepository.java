package com.fpt.adminservice.admin.repository;


import com.fpt.adminservice.enums.PlanStatus;
import com.fpt.adminservice.admin.model.PricePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PricePlanRepository extends JpaRepository<PricePlan, String> {
    Optional<PricePlan> findByIdAndStatus(String s, PlanStatus status);
    Optional<List<PricePlan>> findByStatus(PlanStatus status);

}
