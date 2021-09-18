package com.cinema.project.seance;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.Movie;
import com.cinema.project.user.User;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceControllerTest {

    @Mock
    private SeanceService seanceService;
    @Mock
    private QueryValueResolver queryValueResolver;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;
    @InjectMocks
    private SeanceController seanceController;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");

    @Test
    public void createSeance() {
        ModelAndView expected = new ModelAndView("/cinema/mainpage", true);

        SeanceCreateDto seanceCreateDto = new SeanceCreateDto();
        seanceCreateDto.setDate(expectedSeance.getDate());
        seanceCreateDto.setTime(expectedSeance.getTime());
        seanceCreateDto.setTitle(expectedMovie.getTitle());
        seanceCreateDto.setPrice(expectedSeance.getPrice());
        seanceCreateDto.setSeatingCapacity(expectedSeance.getSeatingCapacity());
        seanceCreateDto.setFreePlaces(expectedSeance.getFreePlaces());

        when(queryValueResolver.getObject(httpServletRequest, SeanceCreateDto.class)).thenReturn(seanceCreateDto);
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(Locale.CANADA);
        when(seanceService.createSeance(seanceCreateDto, Locale.CANADA)).thenReturn(expectedSeance);

        assertEquals(expected, seanceController.createSeance(httpServletRequest));
    }

    @Test
    public void delete() {
        ModelAndView expected = new ModelAndView("/cinema/mainpage", true);

        when(httpServletRequest.getParameter(anyString())).thenReturn(String.valueOf(expectedSeance.getId()));
        when(seanceService.deleteSeanceById(expectedSeance.getId())).thenReturn(expectedSeance);

        assertEquals(expected, seanceController.delete(httpServletRequest));
    }

}