package com.fpt.adminservice.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "TENANT_DB_LIST")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class DBTENANT implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6364902041896237890L;
    @Id
    @Column(name = "DATABASE_ID")
    private String dbId;
    @Column(name = "DB_NAME")
    private String dbName;
    @Column(name = "DB_URL")
    private String url;
    @Column(name = "DB_PASSWORD")
    private String dbPwd;
}