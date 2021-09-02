package com.cinema.project.ticket;

import com.cinema.project.seance.*;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCreateValidator ticketCreateValidator;
    private final SeanceService seanceService;
    private final SeanceRepository seanceRepository;

    public Ticket createTicket(TicketBuyingDto ticketBuyingDto, User user) {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(ticketBuyingDto.getId());
        ticket.setUserId(user.getId());
        return ticketRepository.createTicket(ticketCreateValidator.validate(ticket));
    }

    public List<TicketWithSeanceDto> getTicketsForUser(User user) {
        List<Ticket> tickets = ticketRepository.getTicketsByUserId(user.getId());
        List<Long> ids = tickets.stream()
                .map(Ticket::getSeanceId)
                .collect(Collectors.toList());
        Map<Long, SeanceWithMovieTitleDto> seancesByIds = seanceService.getSeancesByIds(ids);

        return tickets.stream()
                .map(ticket -> new TicketWithSeanceDto(ticket, seancesByIds.get(ticket.getSeanceId())))
                .collect(Collectors.toList());

    }


    //create validator or not? checkFreePlaces in createTicket, so here negativ free places can't be
    //where the method should be?
    public int getFreePlacesForSeance(Long seanceId) {
        if (!seanceRepository.findSeanceByID(seanceId).isPresent()) {
            throw new SeanceExistException("seance doesn't exist");
        }
        int seatingCapacity = ticketCreateValidator.getSeanceCreateValidatorConfig().getMaxSeatingCapacity();
        return seatingCapacity - ticketRepository.getTicketsBySeanceId(seanceId).size();
    }

    public int getAttendance(Long seanceId) {
        if (!seanceRepository.findSeanceByID(seanceId).isPresent()) {
            throw new SeanceExistException("seance doesn't exist");
        }
        return ticketRepository.getTicketsBySeanceId(seanceId).size();
    }

    public Map<Long, ArrayList<Ticket>> getFreePlacesBySeancesIds(List<Long> ids) {
        String paramIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        List<Ticket> tickets = ticketRepository.getTicketsBySeancesIds(paramIds);
        Map<Long, ArrayList<Ticket>> map = new HashMap<>();
        ids.forEach(id->map.put(id,new ArrayList<>()));
        tickets.forEach(ticket -> map.get(ticket.getSeanceId()).add(ticket));
        return map;
    }

}
