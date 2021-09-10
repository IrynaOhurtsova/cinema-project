package com.cinema.project.ticket;

import com.cinema.project.seance.*;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCreateValidator ticketCreateValidator;
    private final SeanceService seanceService;

    public Ticket createTicket(Long seanceId, User user) {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(seanceId);
        ticket.setUserId(user.getId());
        return ticketRepository.createTicket(ticketCreateValidator.validate(ticket));
    }

    public List<TicketWithSeanceDto> getTicketsForUser(User user, Locale locale) {
        List<Ticket> tickets = ticketRepository.getTicketsByUserId(user.getId());
        List<Long> ids = tickets.stream()
                .map(Ticket::getSeanceId)
                .collect(Collectors.toList());
        Map<Long, SeanceWithMovieTitleDto> seancesByIds = seanceService.getSeancesByIds(ids, locale);

        return tickets.stream()
                .map(ticket -> new TicketWithSeanceDto(ticket, seancesByIds.get(ticket.getSeanceId())))
                .collect(Collectors.toList());

    }

    public List<SeanceWithMovieTitleDto> getSeanceForUserByTickets(User user, Locale locale) {
        List<Long> seanceIds = getSeancesIdsByTickets(user);
        Map<Long, SeanceWithMovieTitleDto> seancesByIds = seanceService.getSeancesByIds(seanceIds, locale);
        return new ArrayList<>(seancesByIds.values());
    }

    public List<SeanceWithMovieTitleDto> getSeancesPerPage(User user, String firstValue, Locale locale) {
        return seanceService.getSeancesPerPageByIds(getSeancesIdsByTickets(user),firstValue, locale);
    }

    public Map<Integer, Integer> getPageAndFirstValue(User user) {
        List<Seance> seancesByIds = seanceService.getSeancesByIds(getSeancesIdsByTickets(user));
        return seanceService.findPageAndFirstValue(seancesByIds);
    }

    private List<Long> getSeancesIdsByTickets(User user) {
        return ticketRepository.getTicketsByUserId(user.getId())
                .stream()
                .map(Ticket::getSeanceId)
                .distinct()
                .collect(Collectors.toList());
    }



}
