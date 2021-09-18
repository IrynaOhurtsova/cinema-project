package com.cinema.project.ticket;

import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCreateValidator ticketCreateValidator;

    public Ticket createTicket(Long seanceId, User user) {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(seanceId);
        ticket.setUserId(user.getId());
        return ticketRepository.createTicket(ticketCreateValidator.validate(ticket));
    }

    public List<Ticket> getTicketsForUser(User user) {
        return ticketRepository.getTicketsByUserId(user.getId());
    }

    public List<Long> getSeancesIdsByTickets(User user) {
        return ticketRepository.getTicketsByUserId(user.getId())
                .stream()
                .map(Ticket::getSeanceId)
                .distinct()
                .collect(Collectors.toList());
    }


}
