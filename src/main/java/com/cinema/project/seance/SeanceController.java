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

    public ModelAndView createSeance(HttpServletRequest request) {
        SeanceCreateDto seanceCreateDto = queryValueResolver.getObject(request, SeanceCreateDto.class);
        HttpSession session = request.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        seanceService.createSeance(seanceCreateDto, selectedLocale);
        return new ModelAndView("/cinema/mainpage", true);
    }

    public ModelAndView delete(HttpServletRequest request) {
        String id = request.getParameter("seanceId");
        seanceService.deleteSeanceById(Long.valueOf(id));
        return new ModelAndView("/cinema/mainpage", true);
    }

}
