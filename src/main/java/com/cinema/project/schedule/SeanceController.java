package com.cinema.project.schedule;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

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
}
