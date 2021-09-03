package com.cinema.project.ticket;

import com.cinema.project.seance.*;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCreateValidator ticketCreateValidator;
    private final SeanceService seanceService;

    public Ticket createTicket(TicketCreateDto ticketCreateDto, User user) {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(ticketCreateDto.getSeanceId());
        ticket.setUserId(user.getId());
        return ticketRepository.createTicket(ticketCreateValidator.validate(ticket))
                .orElseThrow(TicketCreateException::new);
    }

    public List<TicketWithSeanceDto> getTicketsForUser(User user) {
        List<Ticket> tickets = ticketRepository.getTicketsByUserId(user.getId());
        if (tickets.size() == 0) {
            throw new TicketExistException("You don't have any tickets");
        }
        List<Long> ids = tickets.stream()
                .map(Ticket::getSeanceId)
                .collect(Collectors.toList());
        Map<Long, SeanceWithMovieTitleDto> seancesByIds = seanceService.getSeancesByIds(ids);

        return tickets.stream()
                .map(ticket -> new TicketWithSeanceDto(ticket, seancesByIds.get(ticket.getSeanceId())))
                .collect(Collectors.toList());

    }

    public List<SeanceWithMovieTitleDto> getSeanceForUserByTickets(User user) {
        List<Ticket> tickets = ticketRepository.getTicketsByUserId(user.getId());
        if (tickets.size() == 0) {
            throw new TicketExistException("You don't have any tickets");
        }
        List<Long> ids = tickets.stream()
                .map(Ticket::getSeanceId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, SeanceWithMovieTitleDto> seancesByIds = seanceService.getSeancesByIds(ids);
        Collection<SeanceWithMovieTitleDto> values = seancesByIds.values();
        return new ArrayList<>(values);
    }

    public int getAttendance(Long seanceId) {
        Seance seanceById = seanceService.getSeanceById(seanceId);
        return ticketRepository.getTicketsBySeanceId(seanceId).size();
    }

    public Map<Long, ArrayList<Ticket>> getFreePlacesBySeancesIds(List<Long> ids) {
        List<Ticket> tickets = ticketRepository.getTicketsBySeancesIds(ids);
        Map<Long, ArrayList<Ticket>> map = new HashMap<>();
        ids.forEach(id->map.put(id,new ArrayList<>()));
        tickets.forEach(ticket -> map.get(ticket.getSeanceId()).add(ticket));
        return map;
    }

}
