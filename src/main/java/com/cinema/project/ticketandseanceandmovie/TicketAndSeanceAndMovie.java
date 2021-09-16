package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.seanceandmovie.SeanceAndMovie;
import com.cinema.project.ticket.Ticket;
import lombok.Value;

@Value
public class TicketAndSeanceAndMovie {

    Ticket ticket;
    SeanceAndMovie seance;
}
