package com.cinema.project.seanceandmovie;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceService;
import com.cinema.project.ticket.TicketService;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceAndMovieServiceTest {

    @Mock
    private SeanceService seanceService;
    @Mock
    private MovieService movieService;
    @Mock
    private TicketService ticketService;

    private SeanceAndMovieService seanceAndMovieService;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceAndMovie seanceAndMovie = new SeanceAndMovie(expectedSeance, expectedMovie);
    private final List<Seance> expectedSeancesList = Collections.singletonList(expectedSeance);
    private final List<Long> ids = Collections.singletonList(expectedSeance.getMovieId());
    private final Map<Long, Movie> moviesById = Collections.singletonMap(expectedMovie.getId(), expectedMovie);
    private final List<SeanceAndMovie> expectedSeancesAndMovieList = Collections.singletonList(seanceAndMovie);
    private final User user = new User(1L, "", "", UserRole.CLIENT);

    @Before
    public void init() {
        seanceAndMovieService = new SeanceAndMovieService(seanceService, movieService, ticketService, 10);
    }

    @Test
    public void getAllSeances() {
        when(seanceService.getAllSeances()).thenReturn(expectedSeancesList);
        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        List<SeanceAndMovie> result = seanceAndMovieService.getAllSeances(Locale.CANADA);

        assertTrue(result.contains(seanceAndMovie));
        assertEquals(expectedSeancesAndMovieList, result);
    }

    @Test
    public void getSeancesByIds() {
        Map<Long, SeanceAndMovie> expected = Collections.singletonMap(seanceAndMovie.getId(), seanceAndMovie);

        when(seanceService.getSeancesByIds(ids)).thenReturn(expectedSeancesList);
        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        Map<Long, SeanceAndMovie> result = seanceAndMovieService.getSeancesByIds(ids, Locale.CANADA);

        assertEquals(expected, result);
    }

    @Test
    public void getSeanceForUserByTickets() {
        when(ticketService.getSeancesIdsByTickets(user)).thenReturn(ids);
        when(seanceService.getSeancesByIds(ids)).thenReturn(expectedSeancesList);
        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        assertEquals(expectedSeancesAndMovieList, seanceAndMovieService.getSeanceForUserByTickets(user, Locale.CANADA));
    }

    @Test
    public void getSeancesPerPage() {
        when(seanceService.getSeancesPerPage(anyInt(), anyString())).thenReturn(expectedSeancesList);
        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        List<SeanceAndMovie> result = seanceAndMovieService.getSeancesPerPage("0", Locale.CANADA);

        assertEquals(expectedSeancesAndMovieList, result);
    }

    @Test
    public void getSeancesPerPageForUser() {
        when(ticketService.getSeancesIdsByTickets(user)).thenReturn(ids);
        when(seanceService.getSeancesPerPageByIds(10, ids, "0")).thenReturn(expectedSeancesList);
        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);

        List<SeanceAndMovie> result = seanceAndMovieService.getSeancesPerPageForUser(user, "0", Locale.CANADA);

        assertEquals(expectedSeancesAndMovieList, result);
    }

    @Test
    public void getPageAndFirstValue() {
        when(seanceService.getAllSeances()).thenReturn(expectedSeancesList);

        Map<Integer, Integer> result = seanceAndMovieService.getPageAndFirstValue();

        assertEquals(Collections.singletonMap(1, 0), result);
    }

    @Test
    public void getPageAndFirstValueForUser() {
        when(ticketService.getSeancesIdsByTickets(user)).thenReturn(ids);
        when(seanceService.getSeancesByIds(ids)).thenReturn(expectedSeancesList);

        Map<Integer, Integer> result = seanceAndMovieService.getPageAndFirstValueForUser(user);

        assertEquals(Collections.singletonMap(1, 0), result);
    }
}