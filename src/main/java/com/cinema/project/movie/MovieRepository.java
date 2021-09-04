package com.cinema.project.movie;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MovieRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public Optional<Movie> getMovieById(Long id) {
        String sql_query = "SELECT * FROM movie WHERE movie.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Movie movie = new Movie(resultSet.getLong("id"),
                        resultSet.getString("title"));
                return Optional.of(movie);
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public List<Movie> getMoviesById(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.emptyList();
        }
        String sql_query = "SELECT * FROM movie WHERE id IN (%s)";
        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        List<Movie> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(sql_query, idsParam));
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getLong("id"), resultSet.getString("title"));
                result.add(movie);
            }
            return result;
        }
    }

    @SneakyThrows
    public Optional<Movie> getMovieByTitle(String title) {
        String sql_query = "SELECT * FROM movie WHERE movie.title = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Movie movie = new Movie(resultSet.getLong("id"),
                        resultSet.getString("title"));
                return Optional.of(movie);
            }
            return Optional.empty();
        }
    }
}
