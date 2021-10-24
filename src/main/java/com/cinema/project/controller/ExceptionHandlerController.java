package com.cinema.project.controller;

import com.cinema.project.movie.MovieNotFoundException;
import com.cinema.project.seance.SeanceCreateException;
import com.cinema.project.ticket.TicketCreateException;
import com.cinema.project.user.UserLoginException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlerController {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserLoginException.class)
    public String handleUserLoginException(Model model, UserLoginException exception) {
        addAttribute(model, exception);
        return "/error/userlogin";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SeanceCreateException.class)
    public String handleSeanceCreateException(Model model, SeanceCreateException exception) {
        addAttribute(model, exception);
        return "/error/creatingisimpossible";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MovieNotFoundException.class)
    public String handleMovieNotFoundException() {
        return "/error/movienotfound";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TicketCreateException.class)
    public String handleTicketCreateException() {
        return "/error/ticketbuying";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String handleException() {
        return "/error/notfound";
    }

    private void addAttribute(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
    }
}
