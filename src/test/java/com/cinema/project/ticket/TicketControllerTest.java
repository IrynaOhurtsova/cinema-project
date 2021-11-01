package com.cinema.project.ticket;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;
    @InjectMocks
    private TicketController ticketController;

    private final User user = new User(1L, "", "", UserRole.CLIENT);
    private final Ticket ticket = new Ticket(1L, 1L, 1L);

    @Test
    public void createTicket() {
        String expected = "redirect:/cinema/mainpage";

        when(ticketService.createTicket(ticket.getSeanceId(), user)).thenReturn(ticket);

        assertEquals(expected, ticketController.createTicket(String.valueOf(ticket.getSeanceId()), user));
    }
}