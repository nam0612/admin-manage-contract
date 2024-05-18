package com.fpt.adminservice.auth.model;

import com.fpt.adminservice.admin.model.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {


    @Id
    @UuidGenerator
    private String id;

    private String email;

    private String phone;

    private String password;

    private String companyName;

    private String taxCode;

    private String presenter;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime startDateUseService;

    private LocalDateTime endDateUseService;

    private LocalDateTime registerDate;

    private Double price;

    private String pricePlan;

    private String file;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
