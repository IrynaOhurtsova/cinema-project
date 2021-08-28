package com.cinema.project.seance;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
public class SeanceController {

    private final SeanceService seanceService;
    private final QueryValueResolver queryValueResolver;

    public ModelAndView allSeances() {
        List<SeanceWithMovieTitleDto> seances = seanceService.getAllSeances();
        ModelAndView modelAndView = ModelAndView.withView("/mainpage.jsp");
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }

    public ModelAndView allSeancesWithDelete() {
        List<SeanceWithMovieTitleDto> seances = seanceService.getAllSeances();
        ModelAndView modelAndView = ModelAndView.withView("/mainpagewithdelete.jsp");
        modelAndView.addAttribute("seances", seances);
        return modelAndView;
    }

    public ModelAndView createSeance(HttpServletRequest request) {
        SeanceCreateDto seanceCreateDto = queryValueResolver.getObject(request, SeanceCreateDto.class);
        Seance seance = seanceService.createSeance(seanceCreateDto);
        HttpSession session = request.getSession();
        session.setAttribute("seance", seance);
        ModelAndView modelAndView = ModelAndView.withView("/seance/createdseance.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Seance seance = seanceService.deleteSeanceById(Long.valueOf(id));
        HttpSession session = request.getSession();
        session.setAttribute("seance", seance);
        return new ModelAndView("/cinema/mainpagewithdelete", true);
    }
}
