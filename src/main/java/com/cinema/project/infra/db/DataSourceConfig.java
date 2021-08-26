package com.cinema.project.infra.db;

import com.cinema.project.infra.config.ConfigLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class DataSourceConfig {

    private final static String PATH_TO_CONFIG = "datasource.yml";

    private final ConfigLoader configLoader;

    public DataSource configureDataSource() {
        DataSourceProperties dataSourceProperties = configLoader.loadConfig(PATH_TO_CONFIG, DataSourceProperties.class);
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperties.jdbcUrl);
        hikariConfig.setUsername(dataSourceProperties.username);
        hikariConfig.setPassword(dataSourceProperties.password);
        return new HikariDataSource(hikariConfig);
    }

    @Data
    private static class DataSourceProperties {
        String jdbcUrl;
        String username;
        String password;
    }
}
