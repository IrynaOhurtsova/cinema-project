package com.cinema.project.schedule;

import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SeanceRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public List<Seance> getAllSeances() {
        String sql_query = "SELECT * FROM seance";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Seance> seances = new ArrayList<>();
            while (resultSet.next()) {
                seances.add(new Seance(resultSet.getLong("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("free_places")));
            }
            return seances;
        }
    }
}
