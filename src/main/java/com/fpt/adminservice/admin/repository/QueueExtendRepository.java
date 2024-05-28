package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.dto.QueueExtendInterface;
import com.fpt.adminservice.admin.model.QueueExtend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueExtendRepository extends JpaRepository<QueueExtend, String> {

    @Query(value = """
        select * from queue_extend qe
                 where 1=1
                 and (qe.status = :status or :status is null)
    """, nativeQuery = true)
    public Page<QueueExtend> getAll(
            @Param("status") String status,
            Pageable pageable
    );

    @Query(value = """
        select * from queue_extend qe
        where 1=1 and qe.company_id = :companyId
        and qe.price_plan_id = :pricePlanId
    """, nativeQuery = true)
    Optional<QueueExtend> findByCompanyIdAndAndPricePlanId(
            @Param("companyId") String companyId,
            @Param("pricePlanId") String pricePlanId);
}

