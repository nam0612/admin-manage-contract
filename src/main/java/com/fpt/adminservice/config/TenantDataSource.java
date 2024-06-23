package com.fpt.adminservice.config;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fpt.adminservice.admin.model.DBTENANT;
import com.fpt.adminservice.admin.repository.DataSourceConfigRepository;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TenantDataSource implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2418234625461365801L;
    private Map<String, DataSource> dataSources = new HashMap<>();

    private final DataSourceConfigRepository configRepo;

    @PostConstruct
    public Map<String, DataSource> getAllTenantDS() {
        List<DBTENANT> dbList = configRepo.findAll();

        Map<String, DataSource> result = dbList.stream()
                .collect(Collectors.toMap(DBTENANT::getDbId, db -> getDataSource(db.getDbId())));

        return result;
    }

    public DataSource getDataSource(String dbId) {
        if (dataSources.get(dbId) != null) {
            return dataSources.get(dbId);
        }
        DataSource dataSource = createDataSource(dbId);
        if (dataSource != null) {
            dataSources.put(dbId, dataSource);
        }
        return dataSource;
    }

    private DataSource createDataSource(String dbId) {
        Optional<DBTENANT> db = configRepo.findById(dbId);
        if (db != null) {
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName("org.hibernate.dialect.MySQL8Dialect")
                    .username(db.get().getDbName())
                    .password(db.get().getDbPwd())
                    .url("jdbc:mysql://" + db.get().getUrl());
            DataSource ds = (DataSource) factory.build();
            return ds;
        }
        return null;
    }
}
