package com.fpt.adminservice.auth.repository;

import com.fpt.adminservice.admin.dto.UserInterface;
import com.fpt.adminservice.auth.model.User;
import com.fpt.adminservice.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Optional<User> findByIdAndStatus(String id, UserStatus status);
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    @Query(value = """
        SELECT u.id,u.company_name as companyName, u.tax_code as taxCode, u.created_date as createdDate, u.end_date_use_service as endDateUseService, u.start_date_use_service as startDateUseService, 
            u.register_date, u.price, u.status, pp.name as planName, pp.id as planId, u.register_date as registerDate, u.email, u.updated_date as updatedDate
                           FROM users u
                           LEFT JOIN price_plan pp ON u.price_plan = pp.id where
                           (lower(u.company_name) like lower(:name) or :name is null)
                           and (lower(u.status) like lower(:status) or :status is null)
                           and (u.start_date_use_service >= :fromDate or  :fromDate is null)
                           and (u.start_date_use_service <= :toDate or :toDate is null)
                           and (u.role = :role)
            """
            , nativeQuery = true)
    Page<UserInterface> search(@Param("name") String name,
                               @Param("status") String status,
                               @Param("fromDate") LocalDate fromDate,
                               @Param("toDate") LocalDate toDate,
                               @Param("role") String role,
                               Pageable pageable);



    @Query(value = """
        SELECT email, end_date_use_service
            FROM users
            WHERE end_date_use_service <= CURRENT_DATE + INTERVAL '7' DAY;
    """, nativeQuery = true)
    List<UserInterface> getEndUserServiceLessTime();

}
