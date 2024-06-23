package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.model.VersionApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<VersionApp, String> {
    @Query(value = """
        select content from version 
        where version = :version
    """, nativeQuery = true)
    String getScriptByVersion(@Param("version") String version);
}
