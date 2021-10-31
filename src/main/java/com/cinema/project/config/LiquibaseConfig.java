package com.cinema.project.config;

import com.cinema.project.infra.config.ConfigLoader;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/property/liquibase/liquibaseconfig.properties")
@RequiredArgsConstructor
public class LiquibaseConfig {

    private final DataSource dataSource;
    private final ConfigLoader configLoader;

    @Value("${pathToConfig.liquibase}")
    private String pathToConfig;

    @Bean
    public SpringLiquibase liquibaseStarter() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        LiquibaseProperties liquibaseProperties = configLoader.loadConfig(pathToConfig, LiquibaseProperties.class);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLogPath());
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

    @Data
    @NoArgsConstructor
    private static class LiquibaseProperties {
        private String changeLogPath;
    }

}
