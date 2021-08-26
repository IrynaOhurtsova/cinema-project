package com.cinema.project.schedule;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ScheduleRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public Schedule getFullSchedule() {
        String sql_query = "SELECT schedule.*, movie.movie_title FROM SCHEDULE INNER JOIN movie " +
                "ON schedule.movie_id = movie.id";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Seance> seances = new ArrayList<>();
            while (resultSet.next()) {
                seances.add(new Seance(resultSet.getLong("id"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("free_places"),
                        resultSet.getString("movie_title")));
            }
            return new Schedule(seances);
        }
    }
}
