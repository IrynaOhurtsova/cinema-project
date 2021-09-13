package com.cinema.project.seance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private SeanceRepository seanceRepository;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);

    @Test
    public void getAllSeances() throws Exception {
        List<Seance> expectedResult = Collections.singletonList(expectedSeance);

        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        List<Seance> result = seanceRepository.getAllSeances();

        assertEquals(expectedResult, result);

    }

    @Test
    public void getAllSeancesShouldReturnEmptyList() throws Exception {
        List<Seance> expectedResult = Collections.emptyList();

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Seance> result = seanceRepository.getAllSeances();

        assertEquals(expectedResult, result);
    }

    @Test
    public void createSeance() throws Exception {
        Seance seance = Seance.builder().date(LocalDate.of(2, 2, 2))
                .time(LocalTime.MIN)
                .movieId(1L)
                .price(50.0)
                .seatingCapacity(300)
                .freePlaces(300).build();

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setDate(1, Date.valueOf(expectedSeance.getDate()));
        doNothing().when(preparedStatement).setTime(2, Time.valueOf(expectedSeance.getTime()));
        doNothing().when(preparedStatement).setLong(3, expectedSeance.getMovieId());
        doNothing().when(preparedStatement).setDouble(4, expectedSeance.getPrice());
        doNothing().when(preparedStatement).setInt(5, expectedSeance.getSeatingCapacity());
        doNothing().when(preparedStatement).setInt(6, expectedSeance.getFreePlaces());
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(expectedSeance.getId());

        Seance result = seanceRepository.createSeance(seance);

        assertEquals(expectedSeance, result);
    }

    @Test
    public void findSeanceByDateAndTime() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setDate(1, Date.valueOf(expectedSeance.getDate()));
        doNothing().when(preparedStatement).setTime(2, Time.valueOf(expectedSeance.getTime()));
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        Optional<Seance> result = seanceRepository.findSeanceByDateAndTime(expectedSeance.getDate(), expectedSeance.getTime());

        assertTrue(result.isPresent());
    }

    @Test
    public void findSeanceByDateAndTimeShouldReturnEmptyOptional() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setDate(1, Date.valueOf(expectedSeance.getDate()));
        doNothing().when(preparedStatement).setTime(2, Time.valueOf(expectedSeance.getTime()));
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<Seance> result = seanceRepository.findSeanceByDateAndTime(expectedSeance.getDate(), expectedSeance.getTime());

        assertFalse(result.isPresent());
    }

    @Test
    public void findSeanceByID() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(1, expectedSeance.getId());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        Optional<Seance> result = seanceRepository.findSeanceByID(expectedSeance.getId());

        assertTrue(result.isPresent());
    }

    @Test
    public void findSeanceByIdShouldReturnEmptyOptional() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(1, expectedSeance.getId());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<Seance> result = seanceRepository.findSeanceByID(expectedSeance.getId());

        assertFalse(result.isPresent());
    }


    //Should I check SQLException?
    @Test
    public void deleteSeanceById() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(1, expectedSeance.getId());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Seance result = seanceRepository.deleteSeanceById(expectedSeance);

        assertEquals(expectedSeance, result);
    }

    @Test
    public void getSeancesByIdsWithEmptyIds() {
        List<Seance> seancesByIds = seanceRepository.getSeancesByIds(Collections.emptyList());

        assertTrue(seancesByIds.isEmpty());
    }

    //Should I check SQLException?
    @Test
    public void getSeancesByIds() throws Exception {
        List<Seance> expected = Collections.singletonList(expectedSeance);
        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        List<Seance> result = seanceRepository.getSeancesByIds(Collections.singletonList(expectedSeance.getId()));

        assertTrue(result.contains(expectedSeance));
        assertEquals(expected, result);
    }

    //Should I check SQLException?
    @Test
    public void getSeancesPerPage() throws Exception {
        List<Seance> expected = Collections.singletonList(expectedSeance);
        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        List<Seance> result = seanceRepository.getSeancesPerPage(0, 10);

        assertTrue(result.contains(expectedSeance));
        assertEquals(expected, result);
    }

    @Test
    public void getSeancesPerPageByIdsWithEmptyIds() {
        List<Seance> seancesByIds = seanceRepository.getSeancesPerPageByIds(Collections.emptyList(), 10, 0);

        assertTrue(seancesByIds.isEmpty());
    }

    @Test
    public void getSeancesPerPageByIds() throws Exception {
        List<Seance> expected = Collections.singletonList(expectedSeance);
        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(resultSet.getLong("id")).thenReturn(expectedSeance.getId());
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(expectedSeance.getDate()));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(expectedSeance.getTime()));
        when(resultSet.getLong("movie_id")).thenReturn(expectedSeance.getMovieId());
        when(resultSet.getDouble("price")).thenReturn(expectedSeance.getPrice());
        when(resultSet.getInt("seating_capacity")).thenReturn(expectedSeance.getSeatingCapacity());
        when(resultSet.getInt("free_Places")).thenReturn(expectedSeance.getFreePlaces());

        List<Seance> result = seanceRepository.getSeancesPerPageByIds(Collections.singletonList(expectedSeance.getId()), 10, 0);

        assertTrue(result.contains(expectedSeance));
        assertEquals(expected, result);
    }
}