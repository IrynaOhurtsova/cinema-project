package com.cinema.project.seanceandmovie;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
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
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;

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
        ModelAndView expected = ModelAndView.withView("");
        expected.addAttribute("seances", expectedSeancesAndMovieList);

        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(seanceAndMovieService.getAllSeances(Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(session.getAttribute("user")).thenReturn(user);
        when(mainPageViewProvider.getModelAndViewForUser(user)).thenReturn(ModelAndView.withView(""));

        ModelAndView result = seanceAndMovieController.allSeances(httpServletRequest);

        assertEquals(expected, result);
    }

    @Test
    public void pagination() {
        Map<Integer, Integer> pageAndFirstValue = Collections.singletonMap(1, 0);

        ModelAndView expected = ModelAndView.withView("");
        expected.addAttribute("pageAndFirstValue", pageAndFirstValue);
        expected.addAttribute("seances", expectedSeancesAndMovieList);

        when(httpServletRequest.getParameter(anyString())).thenReturn("0");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(seanceAndMovieService.getPageAndFirstValue()).thenReturn(pageAndFirstValue);
        when(seanceAndMovieService.getSeancesPerPage("0", Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(session.getAttribute("user")).thenReturn(user);
        when(paginationViewProvider.getModelAndViewForUser(user)).thenReturn(ModelAndView.withView(""));

        ModelAndView result = seanceAndMovieController.pagination(httpServletRequest);

        assertEquals(expected, result);
    }

    @Test
    public void paginationForAvailableSeances() {
        Map<Integer, Integer> pageAndFirstValue = Collections.singletonMap(1, 0);

        ModelAndView expected = ModelAndView.withView("/pages/available.jsp");
        expected.addAttribute("pageAndFirstValue", pageAndFirstValue);
        expected.addAttribute("seances", expectedSeancesAndMovieList);

        when(httpServletRequest.getParameter(anyString())).thenReturn("0");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(seanceAndMovieService.getPageAndFirstValueForUser(user)).thenReturn(pageAndFirstValue);
        when(seanceAndMovieService.getSeancesPerPageForUser(user, "0", Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);
        when(session.getAttribute("user")).thenReturn(user);

        ModelAndView result = seanceAndMovieController.paginationForAvailableSeances(httpServletRequest);

        assertEquals(expected, result);
    }

    @Test
    public void getSeancesForUserByTickets() {
        ModelAndView expected = ModelAndView.withView("/seance/available.jsp");
        expected.addAttribute("seances", expectedSeancesAndMovieList);

        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(seanceAndMovieService.getSeanceForUserByTickets(user, Locale.CANADA)).thenReturn(expectedSeancesAndMovieList);

        ModelAndView result = seanceAndMovieController.getSeancesForUserByTickets(httpServletRequest);

        assertEquals(expected, result);
    }
}