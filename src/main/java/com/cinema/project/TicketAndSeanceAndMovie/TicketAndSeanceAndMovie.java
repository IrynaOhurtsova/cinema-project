package com.cinema.project.TicketAndSeanceAndMovie;

import com.cinema.project.SeanceAndMovie.SeanceAndMovie;
import com.cinema.project.ticket.Ticket;
import lombok.Value;

@Value
public class TicketAndSeanceAndMovie {

    Ticket ticket;
    SeanceAndMovie seance;
}
