package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.seanceandmovie.SeanceAndMovie;
import com.cinema.project.seanceandmovie.SeanceAndMovieService;
import com.cinema.project.ticket.Ticket;
import com.cinema.project.ticket.TicketService;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketAndSeanceAndMovieService {

    private final TicketService ticketService;
    private final SeanceAndMovieService seanceAndMovieService;

    public List<TicketAndSeanceAndMovie> getTicketsForUser(User user, Locale locale) {
        List<Ticket> tickets = ticketService.getTicketsForUser(user, locale);
        List<Long> ids = tickets.stream()
                .map(Ticket::getSeanceId)
                .collect(Collectors.toList());
        Map<Long, SeanceAndMovie> seancesByIds = seanceAndMovieService.getSeancesByIds(ids, locale);

        return tickets.stream()
                .map(ticket -> new TicketAndSeanceAndMovie(ticket, seancesByIds.get(ticket.getSeanceId())))
                .collect(Collectors.toList());

    }

}
