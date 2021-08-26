package com.cinema.project.schedule;

import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ModelAndView fullSchedule() {
        Schedule schedule = scheduleService.fullSchedule();
        ModelAndView modelAndView = ModelAndView.withView("/mainpage.jsp");
        modelAndView.addAttribute("schedule", schedule);
        return modelAndView;
    }
}
