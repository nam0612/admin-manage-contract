package com.fpt.adminservice.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.adminservice.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {


    @Id
    @UuidGenerator
    private String id;

    @UniqueElements
    private String email;

    private String phone;

    @JsonIgnore
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

    private double price;

    private String pricePlan;

    private String file;

    @UniqueElements
    private String userCode;

    private String role;

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
