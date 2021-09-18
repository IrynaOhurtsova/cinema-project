package com.cinema.project.ticket;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;
    @InjectMocks
    private TicketController ticketController;

    private final User user = new User(1L, "", "", UserRole.CLIENT);
    private final Ticket ticket = new Ticket(1L, 1L, 1L);

    @Test
    public void createTicket() {
        ModelAndView expected = new ModelAndView("/cinema/mainpage", true);

        when(httpServletRequest.getParameter(anyString())).thenReturn(String.valueOf(ticket.getSeanceId()));
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(user);
        when(ticketService.createTicket(ticket.getSeanceId(), user)).thenReturn(ticket);

        assertEquals(expected, ticketController.createTicket(httpServletRequest));
    }
}