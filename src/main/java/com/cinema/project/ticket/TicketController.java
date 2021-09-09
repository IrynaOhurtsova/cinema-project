package com.cinema.project.ticket;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.seance.SeanceWithMovieTitleDto;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QueryValueResolver queryValueResolver;

    public ModelAndView createTicket(HttpServletRequest request) {
        String seanceId = request.getParameter("seanceId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ticketService.createTicket(Long.valueOf(seanceId), user);
        ModelAndView modelAndView = ModelAndView.withView("/cinema/mainpage");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView getAllTicketsByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<TicketWithSeanceDto> tickets = ticketService.getTicketsForUser(user, selectedLocale);
        session.setAttribute("tickets", tickets);
        ModelAndView modelAndView = ModelAndView.withView("/ticket/mytickets.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView getSeancesForUserByTickets(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<SeanceWithMovieTitleDto> seances = ticketService.getSeanceForUserByTickets(user, selectedLocale);
        ModelAndView modelAndView = ModelAndView.withView("/seance/available.jsp");
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }

    public ModelAndView paginationForAvailableSeances(HttpServletRequest request) {
        String firstValue = request.getParameter("value");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");

        Map<Integer, Integer> pageAndFirstValue = ticketService.getPageAndFirstValue(user, selectedLocale);
        List<SeanceWithMovieTitleDto> seancesPerPage = ticketService.getSeancesPerPage(user,firstValue, selectedLocale);

        ModelAndView modelAndView = ModelAndView.withView("/pages/available.jsp");
        modelAndView.addAttribute("pageAndFirstValue", pageAndFirstValue);
        modelAndView.addAttribute("seances", seancesPerPage);
        return modelAndView;
    }


}
