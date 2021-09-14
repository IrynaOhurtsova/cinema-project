package com.cinema.project.ticket;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceWithMovieTitleDto;
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
    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceWithMovieTitleDto expectedSeanceDto = new SeanceWithMovieTitleDto(expectedSeance, expectedMovie);
    private final List<SeanceWithMovieTitleDto> expectedSeanceDtoList = Collections.singletonList(expectedSeanceDto);

    @Test
    public void createTicket() {
        ModelAndView expected = new ModelAndView("/cinema/mainpage", true);

        when(httpServletRequest.getParameter(anyString())).thenReturn(String.valueOf(ticket.getSeanceId()));
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(user);
        when(ticketService.createTicket(ticket.getSeanceId(), user)).thenReturn(ticket);

        assertEquals(expected, ticketController.createTicket(httpServletRequest));
    }

    @Test
    public void getAllTicketsByUserId() {
        List<TicketWithSeanceDto> ticketWithSeanceDtos = Collections.singletonList(new TicketWithSeanceDto(ticket, expectedSeanceDto));
        ModelAndView expected = ModelAndView.withView("/ticket/mytickets.jsp");
        expected.addAttribute("tickets", ticketWithSeanceDtos);


        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(ticketService.getTicketsForUser(user,Locale.CANADA)).thenReturn(ticketWithSeanceDtos);

        assertEquals(expected, ticketController.getAllTicketsByUserId(httpServletRequest));

    }

    @Test
    public void getSeancesForUserByTickets() {
        ModelAndView expected = ModelAndView.withView("/seance/available.jsp");
        expected.addAttribute("seances", expectedSeanceDtoList);


        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(ticketService.getSeanceForUserByTickets(user,Locale.CANADA)).thenReturn(expectedSeanceDtoList);

        assertEquals(expected, ticketController.getSeancesForUserByTickets(httpServletRequest));
    }

    @Test
    public void paginationForAvailableSeances() {
        Map<Integer, Integer> pageAndFirstValue = Collections.singletonMap(1, 0);

        ModelAndView expected = ModelAndView.withView("/pages/available.jsp");
        expected.addAttribute("pageAndFirstValue", pageAndFirstValue);
        expected.addAttribute("seances", expectedSeanceDtoList);

        when(httpServletRequest.getParameter(anyString())).thenReturn("0");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("selectedLocale")).thenReturn(Locale.CANADA);
        when(ticketService.getPageAndFirstValue(user)).thenReturn(pageAndFirstValue);
        when(ticketService.getSeancesPerPage(user,"0", Locale.CANADA)).thenReturn(expectedSeanceDtoList);
        when(session.getAttribute("user")).thenReturn(user);

        assertEquals(expected, ticketController.paginationForAvailableSeances(httpServletRequest));
    }
}