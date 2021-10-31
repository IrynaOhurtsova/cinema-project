package com.cinema.project.seanceandmovie;

import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceAndMovieControllerTest {

    @Mock
    private SeanceAndMovieService seanceAndMovieService;
    @Mock
    private SeancesForUserProvider paginationViewProvider;
    @Mock
    private SeancesForUserProvider mainPageViewProvider;
    @Mock
    private Model model;

    private SeanceAndMovieController seanceAndMovieController;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceAndMovie seanceAndMovie = new SeanceAndMovie(expectedSeance, expectedMovie);
    private final List<SeanceAndMovie> expectedSeancesAndMovieList = Collections.singletonList(seanceAndMovie);
    private final User user = new User(1L, "", "", UserRole.CLIENT);

    @Before
    public void init() {
        seanceAndMovieController = new SeanceAndMovieController(seanceAndMovieService, paginationViewProvider, mainPageViewProvider);
    }

    @Test
    public void allSeances() {
        String expected = "";

        when(seanceAndMovieService.getAllSeances(Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(model.addAttribute("seances", expectedSeancesAndMovieList)).thenReturn(model);
        when(mainPageViewProvider.getModelAndViewForUser(user)).thenReturn("");

        String result = seanceAndMovieController.allSeances(Locale.CANADA, user, model);

        assertEquals(expected, result);
    }

    @Test
    public void pagination() {
        Map<Integer, Integer> pageAndFirstValue = Collections.singletonMap(1, 0);

        String expected = "";

        when(seanceAndMovieService.getPageAndFirstValue()).thenReturn(pageAndFirstValue);
        when(seanceAndMovieService.getSeancesPerPage("0", Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(model.addAttribute("pageAndFirstValue", pageAndFirstValue)).thenReturn(model);
        when(model.addAttribute("seances", expectedSeancesAndMovieList)).thenReturn(model);
        when(paginationViewProvider.getModelAndViewForUser(user)).thenReturn("");

        String result = seanceAndMovieController.pagination(String.valueOf(0), Locale.CANADA, user, model);

        assertEquals(expected, result);
    }

    @Test
    public void paginationForAvailableSeances() {
        Map<Integer, Integer> pageAndFirstValue = Collections.singletonMap(1, 0);

        String expected = "/pages/available";

        when(seanceAndMovieService.getPageAndFirstValueForUser(user)).thenReturn(pageAndFirstValue);
        when(seanceAndMovieService.getSeancesPerPageForUser(user, "0", Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(model.addAttribute("pageAndFirstValue", pageAndFirstValue)).thenReturn(model);
        when(model.addAttribute("seances", expectedSeancesAndMovieList)).thenReturn(model);

        String result = seanceAndMovieController.paginationForAvailableSeances(String.valueOf(0), Locale.CANADA, user, model);

        assertEquals(expected, result);
    }

    @Test
    public void getSeancesForUserByTickets() {
        String expected = "/seance/available";

        when(seanceAndMovieService.getSeanceForUserByTickets(user, Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(model.addAttribute("seances", expectedSeancesAndMovieList)).thenReturn(model);

        String result = seanceAndMovieController.getSeancesForUserByTickets(Locale.CANADA, user, model);

        assertEquals(expected, result);
    }
}