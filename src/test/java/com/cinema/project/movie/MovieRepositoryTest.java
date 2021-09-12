package com.cinema.project.movie;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest {

    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private DataSource dataSource;
    @Mock
    private Map<Locale, String> titlesMap;
    @InjectMocks
    private MovieRepository movieRepository;

    @Test
    public void getMoviesByIdWithEmptyIds() {
        List<Movie> moviesById = movieRepository.getMoviesById(Collections.emptyList(), Locale.CANADA);

        assertTrue(moviesById.isEmpty());
    }

    @Test
    public void getMoviesById() throws Exception {
        Movie expectedMovie = new Movie(1L, "title");
        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);
        List<Long> ids = Collections.singletonList(expectedMovie.getId());
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(titlesMap.get(Locale.CANADA)).thenReturn("colunmName");
        when(resultSet.getLong("id")).thenReturn(expectedMovie.getId());
        when(resultSet.getString("colunmName")).thenReturn(expectedMovie.getTitle());

        List<Movie> moviesById = movieRepository.getMoviesById(ids, Locale.CANADA);

        assertTrue(moviesById.contains(expectedMovie));
        assertEquals(1, moviesById.size());
    }

    @Test
    public void getMovieByTitle() throws Exception {
        Movie expectedMovie = new Movie(1L, "title");
        String title = "title";
        when(titlesMap.get(Locale.CANADA)).thenReturn("colunmName");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        preparedStatement.setString(1,title);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedMovie.getId());
        when(resultSet.getString("colunmName")).thenReturn(expectedMovie.getTitle());

        Optional<Movie> movieByTitle = movieRepository.getMovieByTitle(title, Locale.CANADA);

        assertTrue(movieByTitle.isPresent());
    }

    @Test
    public void getMovieByTitleWithWrongTitle() throws Exception {
        String title = "title";
        when(titlesMap.get(Locale.CANADA)).thenReturn("colunmName");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        preparedStatement.setString(1,title);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<Movie> movieByTitle = movieRepository.getMovieByTitle(title, Locale.CANADA);

        assertFalse(movieByTitle.isPresent());
    }

}