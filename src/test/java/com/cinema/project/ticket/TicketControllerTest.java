package com.cinema.project.ticket;

import com.cinema.project.infra.web.QueryValueResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

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

    @Test
    public void createTicket() {
    }

    @Test
    public void getAllTicketsByUserId() {
    }

    @Test
    public void getSeancesForUserByTickets() {
    }

    @Test
    public void paginationForAvailableSeances() {
    }
}