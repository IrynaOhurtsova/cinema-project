package com.cinema.project.config;

import com.cinema.project.config.ConfigLoader;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {

    private static final String PATH = "liquibase.yml";

    private final DataSource dataSource;
    private final ConfigLoader configLoader;

    @Bean
    public SpringLiquibase liquibaseStarter() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        LiquibaseProperties liquibaseProperties = configLoader.loadConfig(PATH, LiquibaseProperties.class);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLogPath());
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

    @SneakyThrows
    public void updateDatabase() {
        LiquibaseProperties liquibaseProperties = configLoader.loadConfig(PATH, LiquibaseProperties.class);
        Connection connection = dataSource.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(liquibaseProperties.getChangeLogPath(), new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Data
    @NoArgsConstructor
    private static class LiquibaseProperties {
        private String changeLogPath;
    }

}
