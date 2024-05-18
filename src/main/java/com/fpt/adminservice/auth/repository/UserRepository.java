package com.fpt.adminservice.auth.repository;

import com.fpt.adminservice.auth.dto.UserInterface;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.auth.model.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    @Query(value = """
        SELECT u.id,u.company_name as companyName, u.tax_code as taxCode, u.created_date, u.end_date_use_service, u.start_date_use_service, 
            u.register_date, u.price, u.status, pp.name, pp.id as planId
                           FROM users u
                           JOIN price_plan pp ON u.price_plan = pp.id where
                            (lower(u.status) like lower(:status) or :status is null)
                            and (lower(u.company_name) like lower(:name) or :name is null)
            """
            , nativeQuery = true)
    Page<UserInterface> search(@Param("name") String name,
                               @Param("status") UserStatus status,
                               Pageable pageable);
}
