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
import static org.mockito.Mockito.*;

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
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(user.getId());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(String.valueOf(user.getRole()));

        assertTrue(userRepository.getUserByLogin(user.getLogin()).isPresent());

        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
    }

    @Test
    public void registerClient() throws Exception {
        User userWithoutId = new User();
        userWithoutId.setLogin(user.getLogin());
        userWithoutId.setPassword(user.getPassword());
        userWithoutId.setRole(user.getRole());

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(user.getId());

        assertEquals(user, userRepository.registerClient(userWithoutId));

        verify(preparedStatement, times(1)).setString(1, userWithoutId.getLogin());
        verify(preparedStatement, times(1)).setString(2, userWithoutId.getPassword());
        verify(preparedStatement, times(1)).setString(3, String.valueOf(userWithoutId.getRole()));
    }
}