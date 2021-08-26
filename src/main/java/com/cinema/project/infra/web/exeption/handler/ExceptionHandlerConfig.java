package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.UserLoginException;

import java.util.Arrays;
import java.util.List;

public class ExceptionHandlerConfig {

    public ExceptionHandler exceptionHandler() {
        List<ExceptionHandlerFunctionHolder> holders = Arrays.asList(
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof UserLoginException,
                        exception -> ModelAndView.withView("/error/userlogin.jsp")));
        return new BaseExceptionHandler(holders, () -> ModelAndView.withView("/error/notfound.jsp"));
    }
}
