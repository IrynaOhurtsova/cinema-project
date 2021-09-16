package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class TicketAndSeanceAndMovieController {

    private final TicketAndSeanceAndMovieService ticketAndSeanceAndMovieService;

    public ModelAndView getAllTicketsByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<TicketAndSeanceAndMovie> tickets = ticketAndSeanceAndMovieService.getTicketsForUser(user, selectedLocale);
        ModelAndView modelAndView = ModelAndView.withView("/ticket/mytickets.jsp");
        modelAndView.addAttribute("tickets", tickets);
        return modelAndView;
    }


}
