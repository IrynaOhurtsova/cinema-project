package com.cinema.project.seanceandmovie;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class SeanceAndMovieController {

    private final SeanceAndMovieService seanceAndMovieService;
    private final SeancesForUserProvider paginationViewProvider;
    private final SeancesForUserProvider mainPageViewProvider;

    public ModelAndView allSeances(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<SeanceAndMovie> seances = seanceAndMovieService.getAllSeances(selectedLocale);
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = mainPageViewProvider.getModelAndViewForUser(user);
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }

    public ModelAndView pagination(HttpServletRequest request) {
        String firstValue = request.getParameter("value");
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");

        Map<Integer, Integer> pageAndFirstValue = seanceAndMovieService.getPageAndFirstValue();
        List<SeanceAndMovie> seancesPerPage = seanceAndMovieService.getSeancesPerPage(firstValue, selectedLocale);

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = paginationViewProvider.getModelAndViewForUser(user);
        modelAndView.addAttribute("pageAndFirstValue", pageAndFirstValue);
        modelAndView.addAttribute("seances", seancesPerPage);
        return modelAndView;
    }

    public ModelAndView paginationForAvailableSeances(HttpServletRequest request) {
        String firstValue = request.getParameter("value");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");

        Map<Integer, Integer> pageAndFirstValue = seanceAndMovieService.getPageAndFirstValueForUser(user);
        List<SeanceAndMovie> seancesPerPage = seanceAndMovieService.getSeancesPerPageForUser(user, firstValue, selectedLocale);

        ModelAndView modelAndView = ModelAndView.withView("/pages/available.jsp");
        modelAndView.addAttribute("pageAndFirstValue", pageAndFirstValue);
        modelAndView.addAttribute("seances", seancesPerPage);
        return modelAndView;
    }

    public ModelAndView getSeancesForUserByTickets(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<SeanceAndMovie> seances = seanceAndMovieService.getSeanceForUserByTickets(user, selectedLocale);
        ModelAndView modelAndView = ModelAndView.withView("/seance/available.jsp");
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }
}
