package com.cinema.project.ticket;

import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceExistException;
import com.cinema.project.seance.SeanceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class TicketCreateValidator {

    private final SeanceRepository seanceRepository;

    public Ticket validate(Ticket ticket) {
        Optional<Seance> seanceByID = seanceRepository.findSeanceByID(ticket.getSeanceId());

        if (!seanceByID.isPresent()) {
            throw new SeanceExistException("seance doesn't exist");
        }
        if (seanceByID.get().getFreePlaces() == 0) {
            throw new TicketCreateException();
        }
        return ticket;
    }

}
