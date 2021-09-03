package com.cinema.project.ticket;

public class TicketExistException extends RuntimeException {
    public TicketExistException(String message) {
        super(message);
    }
}
