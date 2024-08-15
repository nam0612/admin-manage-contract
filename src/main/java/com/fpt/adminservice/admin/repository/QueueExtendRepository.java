package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.dto.QueueExtendInterface;
import com.fpt.adminservice.admin.model.QueueExtend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueueExtendRepository extends JpaRepository<QueueExtend, String> {

    @Query(value = """
        select * from queue_extend qe
                 where 1=1
                 and (qe.status = :status or :status is null)
                 and (lower(qe.company_name) like lower(:name) or :name is null)
                 and (qe.created_date >= :fromDate or :fromDate is null)
                 and (qe.created_date <= :toDate or :toDate is null)
                 order by qe.created_date desc
    """, nativeQuery = true)
    Page<QueueExtend> getAll(
            @Param("status") String status,
            @Param("name") String name,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );

    @Query(value = """
        select * from queue_extend qe
        where 1=1 and qe.company_id = :companyId
        and qe.price_plan_id = :pricePlanId
        and (lower(qe.status) like lower(:status) or :status is null)
    """, nativeQuery = true)
    Optional<QueueExtend> findByCompanyIdAndAndPricePlanId(
            @Param("companyId") String companyId,
            @Param("pricePlanId") String pricePlanId,
            @Param("status") String status);

    Optional<QueueExtend> findByPaymentId(Integer paymentId);

    Page<QueueExtend> findByCompanyId(String companyId, Pageable pageable);

    @Query(value = """
        select * from queue_extend qe
        where 1=1 and qe.company_id = :companyId
        and (qe.status = :status)
    """, nativeQuery = true)
    Optional<QueueExtend> findByCompanyIdAndStatus(
            @Param("companyId") String companyId,
            @Param("status") String status);
}

