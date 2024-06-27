package com.fpt.adminservice.version.repository;

import com.fpt.adminservice.version.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    Optional<Version> findByVersion(String versionId);
}
