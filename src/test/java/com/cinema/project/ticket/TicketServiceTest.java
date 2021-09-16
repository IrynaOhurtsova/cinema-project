package com.cinema.project.ticket;

import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceService;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketCreateValidator ticketCreateValidator;
    @Mock
    private SeanceService seanceService;
    @InjectMocks
    private TicketService ticketService;

    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);
    private final User user = new User();
    private final Ticket ticket = new Ticket();
    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceWithMovieTitleDto expectedSeanceDto = new SeanceWithMovieTitleDto(expectedSeance, expectedMovie);
    private final Map<Long, SeanceWithMovieTitleDto> seancesByIds = Collections.singletonMap(expectedSeanceDto.getId(), expectedSeanceDto);
    private final List<Long> ids = Collections.singletonList(expectedSeance.getId());
    private final List<Ticket> expectedTickets = Collections.singletonList(expectedTicket);
    private final List<SeanceWithMovieTitleDto> expectedSeanceDtoList = Collections.singletonList(expectedSeanceDto);

    @Before
    public void beforeEachTest() {
        ticket.setSeanceId(1L);
        ticket.setUserId(1L);
        user.setId(1L);
        user.setRole(UserRole.CLIENT);
    }

    @Test
    public void createTicket() {
        when(ticketCreateValidator.validate(ticket)).thenReturn(ticket);
        when(ticketRepository.createTicket(ticket)).thenReturn(expectedTicket);

        assertEquals(expectedTicket, ticketService.createTicket(ticket.getSeanceId(), user));
    }

//    @Test
//    public void getTicketsForUser() {
//        List<TicketWithSeanceDto> expected = Collections.singletonList(new TicketWithSeanceDto(expectedTicket, expectedSeanceDto));
//
//        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);
//        when(seanceService.getSeancesByIds(ids, Locale.CANADA)).thenReturn(seancesByIds);
//
//        assertEquals(expected, ticketService.getTicketsForUserDto(user, Locale.CANADA));
//    }
//
//    @Test
//    public void getSeanceForUserByTickets() {
//        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);
//        when(seanceService.getSeancesByIds(ids, Locale.CANADA)).thenReturn(seancesByIds);
//
//        assertEquals(expectedSeanceDtoList, ticketService.getSeanceForUserByTickets(user, Locale.CANADA));
//    }
//
//    @Test
//    public void getSeancesPerPage() {
//        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);
//        when(seanceService.getSeancesPerPageByIdsDto(ids, "0", Locale.CANADA)).thenReturn(expectedSeanceDtoList);
//
//        assertEquals(expectedSeanceDtoList, ticketService.getSeancesPerPage(user, "0", Locale.CANADA));
//    }
//
//    @Test
//    public void getPageAndFirstValue() {
//        Map<Integer, Integer> expected = Collections.singletonMap(1, 0);
//
//        List<Seance> seances = Collections.singletonList(expectedSeance);
//
//        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);
//        when(seanceService.getSeancesByIds(ids)).thenReturn(seances);
//        when(seanceService.findPageAndFirstValue(seances)).thenReturn(expected);
//
//        assertEquals(expected, ticketService.getPageAndFirstValue(user));
//
//    }
}