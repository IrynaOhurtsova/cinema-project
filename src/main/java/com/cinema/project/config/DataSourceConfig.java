package com.cinema.project.config;

import com.cinema.project.infra.config.ConfigLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final ConfigLoader configLoader;

    @Value("${pathToConfig.datasource}")
    private String pathToConfig;

    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = configLoader.loadConfig(pathToConfig, DataSourceProperties.class);
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
