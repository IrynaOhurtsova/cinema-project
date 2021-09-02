package com.cinema.project.ticket;

import com.cinema.project.seance.SeanceCreateValidatorConfig;
import com.cinema.project.seance.SeanceExistException;
import com.cinema.project.seance.SeanceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TicketCreateValidator {

    private final TicketRepository ticketRepository;
    private final SeanceRepository seanceRepository;
    private final SeanceCreateValidatorConfig seanceCreateValidatorConfig;

    public Ticket validate(Ticket ticket) {
        if (!checkSeance(ticket.getSeanceId())) {
            throw new SeanceExistException("seance doesn't exist");
        }
        if (!checkFreePlaces(ticket.getSeanceId())) {
            throw new TicketCreateException();
        }
        return ticket;
    }

    private boolean checkSeance(Long seanceId) {
        return seanceRepository.findSeanceByID(seanceId).isPresent();
    }

    private boolean checkFreePlaces(Long seanceId) {
        List<Ticket> tickets = ticketRepository.getTicketsBySeanceId(seanceId);
        return tickets.size() < seanceCreateValidatorConfig.getMaxSeatingCapacity();
    }
}
