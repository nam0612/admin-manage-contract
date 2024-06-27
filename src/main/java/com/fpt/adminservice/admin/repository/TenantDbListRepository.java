package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.model.TenantDbList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantDbListRepository extends JpaRepository<TenantDbList, String> {
    Optional<TenantDbList> findByTenantId(String tenantId);
}
