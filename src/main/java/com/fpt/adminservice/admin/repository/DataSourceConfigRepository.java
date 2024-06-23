package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.model.DBTENANT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceConfigRepository extends JpaRepository<DBTENANT, String> {
}

