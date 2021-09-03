package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.MovieNotFoundException;
import com.cinema.project.seance.SeanceCreateException;
import com.cinema.project.ticket.TicketCreateException;
import com.cinema.project.ticket.TicketExistException;
import com.cinema.project.user.UserLoginException;

import java.util.Arrays;
import java.util.List;

public class ExceptionHandlerConfig {

    public ExceptionHandler exceptionHandler() {
        List<ExceptionHandlerFunctionHolder> holders = Arrays.asList(
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof UserLoginException,
                        exception -> ModelAndView.withView("/error/userlogin.jsp")
                                .addAttribute("message", exception.getMessage())),
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof SeanceCreateException,
                        exception -> ModelAndView.withView("/error/creatingisimpossible.jsp")
                                .addAttribute("message", exception.getMessage())),
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof MovieNotFoundException,
                        exception -> ModelAndView.withView("/error/movienotfound.jsp")),
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof TicketCreateException,
                        exception -> ModelAndView.withView("/error/ticketbuying.jsp")),
                new ExceptionHandlerFunctionHolder(exception -> exception instanceof TicketExistException,
                        exception -> ModelAndView.withView("/error/ticketexist.jsp")));
        return new BaseExceptionHandler(holders, () -> ModelAndView.withView("/error/notfound.jsp"));
    }
}
