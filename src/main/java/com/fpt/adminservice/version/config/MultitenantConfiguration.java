package com.fpt.adminservice.version.config;

import com.fpt.adminservice.admin.repository.TenantDbListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;


@Service
@RequiredArgsConstructor
public class MultitenantConfiguration {

    private final TenantDbListRepository dataSourceConfigRepository;
    public DataSource dataSource(String tenantId) {
        var dbDetails = dataSourceConfigRepository.findById(tenantId);


        if (dbDetails != null) {
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
                    .username(dbDetails.get().getUsername())
                    .password(dbDetails.get().getPassword())
                    .url(dbDetails.get().getUrl());
            DataSource ds = (DataSource) factory.build();
            return ds;
        }
        return null;
    }

}
