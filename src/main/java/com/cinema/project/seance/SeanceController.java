package com.cinema.project.seance;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class SeanceController {

    private final SeanceService seanceService;
    private final QueryValueResolver queryValueResolver;
    private final SeancesForUserProvider paginationProvider;
    private final SeancesForUserProvider mainPageProvider;

    public ModelAndView allSeances(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        List<SeanceWithMovieTitleDto> seances = seanceService.getAllSeances(selectedLocale);
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = mainPageProvider.getModelAmdViewForUser(user);
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }

    public ModelAndView createSeance(HttpServletRequest request) {
        SeanceCreateDto seanceCreateDto = queryValueResolver.getObject(request, SeanceCreateDto.class);
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        seanceService.createSeance(seanceCreateDto, selectedLocale);
        ModelAndView modelAndView = ModelAndView.withView("/cinema/mainpage");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView delete(HttpServletRequest request) {
        String id = request.getParameter("seanceId");
        seanceService.deleteSeanceById(Long.valueOf(id));
        return new ModelAndView("/cinema/mainpage", true);
    }

    public ModelAndView pagination(HttpServletRequest request) {
        String firstValue = request.getParameter("value");
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");

        Map<Integer, Integer> pageAndFirstValue = seanceService.getPageAndFirstValue();
        List<SeanceWithMovieTitleDto> seancesPerPage = seanceService.getSeancesPerPage(firstValue, selectedLocale);

        session.setAttribute("pageAndFirstValue", pageAndFirstValue);
        session.setAttribute("seances", seancesPerPage);

        User user = (User) session.getAttribute("user");
        return paginationProvider.getModelAmdViewForUser(user);
    }


}
