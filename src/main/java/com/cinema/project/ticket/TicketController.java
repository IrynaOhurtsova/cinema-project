package com.cinema.project.ticket;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    public ModelAndView createTicket(HttpServletRequest request) {
        String seanceId = request.getParameter("seanceId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ticketService.createTicket(Long.valueOf(seanceId), user);
        return new ModelAndView("/cinema/mainpage", true);
    }

}
