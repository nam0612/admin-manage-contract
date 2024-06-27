package com.fpt.adminservice.version.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "version")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Version {
    @Id
    @UuidGenerator
    private String id;

    @Column(unique = true)
    private String version;

    private String script;

    private String status;
}
