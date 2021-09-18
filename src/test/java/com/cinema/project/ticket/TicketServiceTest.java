package com.cinema.project.ticket;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketCreateValidator ticketCreateValidator;
    @InjectMocks
    private TicketService ticketService;

    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);
    private final User user = new User();
    private final Ticket ticket = new Ticket();
    private final List<Ticket> expectedTickets = Collections.singletonList(expectedTicket);

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

    @Test
    public void getTicketsForUser() {
        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);

        assertEquals(expectedTickets, ticketService.getTicketsForUser(user));
    }

    @Test
    public void getSeancesIdsByTickets() {
        when(ticketRepository.getTicketsByUserId(user.getId())).thenReturn(expectedTickets);

        assertEquals(Collections.singletonList(1L), ticketService.getSeancesIdsByTickets(user));
    }

}