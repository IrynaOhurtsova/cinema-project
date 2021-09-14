package com.cinema.project.infra.db;

import com.cinema.project.infra.config.ConfigLoader;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
public class LiquibaseStarter {

    private static final String PATH = "liquibase.yml";

    private final DataSource dataSource;
    private final ConfigLoader configLoader;

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
