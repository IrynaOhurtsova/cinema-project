package com.cinema.project.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private UserRepository userRepository;

    private final User user = new User(1L, "login", "password", UserRole.CLIENT);

    @Test
    public void getUserByLogin() throws Exception {
        User user = new User(1L, "login", "password", UserRole.CLIENT);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(user.getId());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(String.valueOf(user.getRole()));

        assertTrue(userRepository.getUserByLogin(user.getLogin()).isPresent());
    }

    @Test
    public void registerClient() throws Exception {
        User userWithoutId = new User();
        userWithoutId.setLogin(user.getLogin());
        userWithoutId.setPassword(user.getPassword());
        userWithoutId.setRole(user.getRole());

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(1, userWithoutId.getLogin());
        doNothing().when(preparedStatement).setString(2, userWithoutId.getPassword());
        doNothing().when(preparedStatement).setString(3, String.valueOf(userWithoutId.getRole()));
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(user.getId());

        assertEquals(user, userRepository.registerClient(userWithoutId));
    }
}