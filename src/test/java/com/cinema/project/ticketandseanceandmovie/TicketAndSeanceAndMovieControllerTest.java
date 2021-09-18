package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seanceandmovie.SeanceAndMovie;
import com.cinema.project.ticket.Ticket;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketAndSeanceAndMovieControllerTest {

    @Mock
    private TicketAndSeanceAndMovieService ticketAndSeanceAndMovieService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;
    @InjectMocks
    private TicketAndSeanceAndMovieController ticketAndSeanceAndMovieController;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceAndMovie seanceAndMovie = new SeanceAndMovie(expectedSeance, expectedMovie);
    private final User user = new User(1L, "", "", UserRole.CLIENT);
    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);
    private final SeanceAndMovie expectedSeanceAndMovie = new SeanceAndMovie(expectedSeance, expectedMovie);
    private final TicketAndSeanceAndMovie ticketAndSeanceAndMovie = new TicketAndSeanceAndMovie(expectedTicket, expectedSeanceAndMovie);
    private final List<TicketAndSeanceAndMovie> ticketAndSeanceAndMovieList = Collections.singletonList(ticketAndSeanceAndMovie);

    @Test
    public void getAllTicketsByUserId() {
        ModelAndView modelAndView = ModelAndView.withView("/ticket/mytickets.jsp");
        modelAndView.addAttribute("tickets", ticketAndSeanceAndMovieList);

        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(ticketAndSeanceAndMovieService.getTicketsForUser(user, Locale.CANADA)).thenReturn(ticketAndSeanceAndMovieList);

        assertEquals(modelAndView, ticketAndSeanceAndMovieController.getAllTicketsByUserId(httpServletRequest));
    }
}