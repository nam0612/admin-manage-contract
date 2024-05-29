package com.fpt.adminservice.admin.repository;

import com.fpt.adminservice.admin.model.MailAuthedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailAuthenCodeRepository extends JpaRepository<MailAuthedCode, String> {

    Optional<MailAuthedCode> findByEmail(String code);
}
