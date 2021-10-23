package com.cinema.project.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final static String PATH_TO_CONFIG = "datasource.yml";

    private final ConfigLoader configLoader;

    @Bean
    public DataSource dataSource() {
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
