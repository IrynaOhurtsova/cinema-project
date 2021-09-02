package com.cinema.project.ticket;

import com.cinema.project.seance.SeanceWithMovieTitleDto;
import lombok.Value;

@Value
public class TicketWithSeanceDto {

    Ticket ticket;
    SeanceWithMovieTitleDto seance;
}
