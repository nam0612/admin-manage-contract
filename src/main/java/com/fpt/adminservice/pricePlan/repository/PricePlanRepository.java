package com.fpt.adminservice.pricePlan.repository;

import com.fpt.adminservice.pricePlan.model.PlanStatus;
import com.fpt.adminservice.pricePlan.model.PricePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PricePlanRepository  extends JpaRepository<PricePlan, String> {
    Optional<PricePlan> findById(String s);
    Optional<List<PricePlan>> findByStatus(PlanStatus status);

}
