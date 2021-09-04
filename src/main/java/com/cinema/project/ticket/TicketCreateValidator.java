package com.cinema.project.ticket;

import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketCreateValidator {

    private final SeanceService seanceService;

    public Ticket validate(Ticket ticket) {
        Seance seance = seanceService.getSeanceById(ticket.getSeanceId());
        if (seance.getFreePlaces() == 0) {
            throw new TicketCreateException();
        }
        return ticket;
    }
}
