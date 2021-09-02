package com.cinema.project.ticket;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QueryValueResolver queryValueResolver;

    public ModelAndView createTicket(HttpServletRequest request) {
        TicketBuyingDto ticketBuyingDto = queryValueResolver.getObject(request, TicketBuyingDto.class);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Ticket ticket = ticketService.createTicket(ticketBuyingDto, user);
        session.setAttribute("ticket", ticket);
        ModelAndView modelAndView = ModelAndView.withView("/ticket/buyinghasdone.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView getAllTicketsByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<TicketWithSeanceDto> tickets = ticketService.getTicketsForUser(user);
        session.setAttribute("tickets", tickets);
        ModelAndView modelAndView = ModelAndView.withView("/ticket/mytickets.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView getFreePlacesForSeance(HttpServletRequest request) {
        TicketBuyingDto ticketBuyingDto = queryValueResolver.getObject(request, TicketBuyingDto.class);
        int freePlacesForSeance = ticketService.getFreePlacesForSeance(ticketBuyingDto.getId());
        ModelAndView modelAndView = ModelAndView.withView("/seance/freeplaces.jsp");
        modelAndView.addAttribute("freePlaces", freePlacesForSeance);
        return modelAndView;
    }

    public ModelAndView getAttendance(HttpServletRequest request) {
        TicketBuyingDto ticketBuyingDto = queryValueResolver.getObject(request, TicketBuyingDto.class);
        int attendance = ticketService.getAttendance(ticketBuyingDto.getId());
        HttpSession session = request.getSession();
        session.setAttribute("attendance", attendance);
        ModelAndView modelAndView = ModelAndView.withView("/seance/attendance.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
