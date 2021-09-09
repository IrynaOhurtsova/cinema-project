package com.cinema.project.movie;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MovieRepository {

    private final DataSource dataSource;
    private final Map<Locale, String> titleColumns;

    @SneakyThrows
    public List<Movie> getMoviesById(List<Long> ids, Locale locale) {
        if (ids.isEmpty()) {
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
            String column = titleColumns.get(locale);
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getLong("id"), resultSet.getString(column));
                result.add(movie);
            }
            return result;
        }
    }

    @SneakyThrows
    public Optional<Movie> getMovieByTitle(String title, Locale locale) {
        String prepare_sql_query = "SELECT * FROM movie WHERE (%s) = ?";
        String column = titleColumns.get(locale);
        String sql_query = String.format(prepare_sql_query, column);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Movie movie = new Movie(resultSet.getLong("id"), resultSet.getString(column));
                return Optional.of(movie);
            }
            return Optional.empty();
        }
    }
}
