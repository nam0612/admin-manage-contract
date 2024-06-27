package com.fpt.adminservice.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TENANT_DB_LIST")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDbList implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6364902041896237890L;
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "url")
    private String url;
    @Column(name = "password")
    private String password;
    @Column(name = "tenant_id", unique = true)
    private String tenantId;
    @Column(name = "version")
    private String version;
    @Column(name = "status")
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
