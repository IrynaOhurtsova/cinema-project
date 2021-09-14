package com.cinema.project.ticket;

import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceService;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketCreateValidatorTest {

    @Mock
    private SeanceService seanceService;
    @InjectMocks
    private TicketCreateValidator ticketCreateValidator;

    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);

    @Test
    public void validate() {
        Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);

        when(seanceService.getSeanceById(expectedTicket.getSeanceId())).thenReturn(expectedSeance);

        assertEquals(expectedTicket, ticketCreateValidator.validate(expectedTicket));
    }

    @Test(expected = TicketCreateException.class)
    public void validationNotSuccess() {
        Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 0);

        when(seanceService.getSeanceById(expectedTicket.getSeanceId())).thenReturn(expectedSeance);

        ticketCreateValidator.validate(expectedTicket);
    }


}