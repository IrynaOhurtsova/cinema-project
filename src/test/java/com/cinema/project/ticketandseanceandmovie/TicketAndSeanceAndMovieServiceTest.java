package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import com.cinema.project.seanceandmovie.SeanceAndMovie;
import com.cinema.project.seanceandmovie.SeanceAndMovieService;
import com.cinema.project.ticket.Ticket;
import com.cinema.project.ticket.TicketService;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketAndSeanceAndMovieServiceTest {

    @Mock
    private TicketService ticketService;
    @Mock
    private SeanceAndMovieService seanceAndMovieService;
    @InjectMocks
    private TicketAndSeanceAndMovieService ticketAndSeanceAndMovieService;

    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);
    private final User user = new User(1L, "", "", UserRole.CLIENT);
    private final List<Ticket> expectedTickets = Collections.singletonList(expectedTicket);
    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceAndMovie expectedSeanceAndMovie = new SeanceAndMovie(expectedSeance, expectedMovie);
    private final Map<Long, SeanceAndMovie> seancesByIds = Collections.singletonMap(expectedSeanceAndMovie.getId(), expectedSeanceAndMovie);
    private final List<Long> ids = Collections.singletonList(expectedSeance.getId());
    private final TicketAndSeanceAndMovie ticketAndSeanceAndMovie = new TicketAndSeanceAndMovie(expectedTicket, expectedSeanceAndMovie);
    private final List<TicketAndSeanceAndMovie> ticketAndSeanceAndMovieList = Collections.singletonList(ticketAndSeanceAndMovie);

    @Test
    public void getTicketsForUser() {
        when(ticketService.getTicketsForUser(user)).thenReturn(expectedTickets);
        when(seanceAndMovieService.getSeancesByIds(ids, Locale.CANADA)).thenReturn(seancesByIds);

        assertEquals(ticketAndSeanceAndMovieList, ticketAndSeanceAndMovieService.getTicketsForUser(user, Locale.CANADA));
    }
}