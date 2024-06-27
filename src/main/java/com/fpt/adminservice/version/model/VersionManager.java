package com.fpt.adminservice.version.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "version_manager")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionManager {
    @Id
    @UuidGenerator
    private String id;

    @Column(unique = true, nullable = false)
    private String tenantId;

    private String version;
}
