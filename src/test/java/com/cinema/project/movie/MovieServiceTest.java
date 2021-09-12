package com.cinema.project.movie;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getMoviesByIdWithEmptyIds() {
        Map<Long, Movie> moviesById = movieService.getMoviesById(Collections.emptyList(), Locale.CANADA);

        assertTrue(moviesById.isEmpty());
    }

    @Test
    public void getMoviesById() {
        Movie expectedMovie = new Movie(1L, "title");
        Map<Long, Movie> expected = Collections.singletonMap(expectedMovie.getId(), expectedMovie);

        List<Long> ids = Collections.singletonList(1L);
        List<Movie> moviesById = Collections.singletonList(expectedMovie);
        when(movieRepository.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        Map<Long, Movie> result = movieService.getMoviesById(ids, Locale.CANADA);

        assertEquals(expected, result);
    }

    @Test(expected = MovieNotFoundException.class)
    public void getMovieByTitleWithWrongTitleShouldThrowsException() {
        String title = "wrong title";
        when(movieRepository.getMovieByTitle(title, Locale.CANADA)).thenReturn(Optional.empty());

        movieService.getMovieByTitle(title, Locale.CANADA);

    }

    @Test
    public void getMovieByTitleWithWrongTitleShouldThrowsExceptionWithMessage() {
        exception.expect(MovieNotFoundException.class);
        exception.expectMessage("wrong title of movie");

        String title = "wrong title";
        when(movieRepository.getMovieByTitle(title, Locale.CANADA)).thenReturn(Optional.empty());

        movieService.getMovieByTitle(title, Locale.CANADA);

    }

    @Test
    public void getMovieByTitle() {
        String title = "title";
        Movie expectedMovie = new Movie(1L, title);
        when(movieRepository.getMovieByTitle(title, Locale.CANADA)).thenReturn(Optional.of(expectedMovie));

        Movie movieByTitle = movieService.getMovieByTitle(title, Locale.CANADA);

        assertEquals(expectedMovie, movieByTitle);

    }
}