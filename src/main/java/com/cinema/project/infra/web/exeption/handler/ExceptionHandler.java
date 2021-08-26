package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;

public interface ExceptionHandler {

    ModelAndView handle(Exception exception);
}
