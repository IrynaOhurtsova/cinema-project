package com.cinema.project.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public Optional<User> getUserByLogin(String login) {
        String sqlQuery = "SELECT * FROM user WHERE login IN (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        UserRole.valueOf(resultSet.getString("role")));
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


    @SneakyThrows
    public User registerClient(User user) {
        String sqlQuery = "INSERT INTO user (login, password, role) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getRole()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
            return user;
        }
    }
}
