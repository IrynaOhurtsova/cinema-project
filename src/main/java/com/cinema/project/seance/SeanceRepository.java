package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SeanceRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public List<Seance> getAllSeances() {
        String sql_query = "SELECT * FROM seance";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Seance> seances = new ArrayList<>();
            while (resultSet.next()) {
                seances.add(new Seance(resultSet.getLong("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("seating_capacity"),
                        resultSet.getInt("free_Places")));
            }
            return seances;
        }
    }

    @SneakyThrows
    public Seance createSeance(Seance seance) {
        String sql_query = "INSERT INTO seance (date, time, movie_id, price, seating_capacity, free_places) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(seance.getDate()));
            preparedStatement.setTime(2, Time.valueOf(seance.getTime()));
            preparedStatement.setLong(3, seance.getMovieId());
            preparedStatement.setDouble(4, seance.getPrice());
            preparedStatement.setInt(5, seance.getSeatingCapacity());
            preparedStatement.setInt(6, seance.getFreePlaces());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong(1);
            seance.setId(id);
            return seance;
        }
    }

    @SneakyThrows
    public Optional<Seance> findSeanceByDateAndTime(LocalDate date, LocalTime time) {
        String sql_query = "SELECT * FROM seance WHERE date = DATE (?) AND time = TIME (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Seance((resultSet.getLong("id")),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("seating_capacity"),
                        resultSet.getInt("free_Places")));
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<Seance> findSeanceByID(Long id) {
        String sql_query = "SELECT * FROM seance WHERE id IN (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Seance((resultSet.getLong("id")),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("seating_capacity"),
                        resultSet.getInt("free_Places")));
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Seance deleteSeanceById(Seance seance) {
        String sql_query = "DELETE FROM seance WHERE id IN (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setLong(1, seance.getId());
            preparedStatement.executeUpdate();
            return seance;
        }
    }

    @SneakyThrows
    public List<Seance> getSeancesByIds(List<Long> ids) {
        if(ids.isEmpty()) {
            return Collections.emptyList();
        }
        String sql_query = "SELECT * FROM seance WHERE id IN (%s)";
        List<Seance> seances = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String idsParam = ids.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            ResultSet resultSet = statement.executeQuery(String.format(sql_query,idsParam));
            while (resultSet.next()) {
                seances.add(new Seance((resultSet.getLong("id")),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getLong("movie_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("seating_capacity"),
                        resultSet.getInt("free_places")));
            }
            return seances;
        }
    }

}
