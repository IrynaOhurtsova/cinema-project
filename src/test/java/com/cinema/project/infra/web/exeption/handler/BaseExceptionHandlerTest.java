package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.movie.MovieNotFoundException;
import com.cinema.project.seance.SeanceCreateException;
import com.cinema.project.user.UserLoginException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BaseExceptionHandlerTest {

    private final ExceptionHandlerFunctionHolder exceptionHandlerFunctionHolder =
            new ExceptionHandlerFunctionHolder(exception -> exception instanceof UserLoginException, exception -> ModelAndView.withView(exception.getMessage()));
    private final Supplier<ModelAndView> defaultResultSupplier = () -> ModelAndView.withView("default view");
    private final List<ExceptionHandlerFunctionHolder> exceptionHandlerFunctionHolders = Collections.singletonList(exceptionHandlerFunctionHolder);
    private final BaseExceptionHandler baseExceptionHandler = new BaseExceptionHandler(exceptionHandlerFunctionHolders, defaultResultSupplier);

    @Test
    public void handle() {
        ModelAndView modelAndView = baseExceptionHandler.handle(new UserLoginException("message"));

        assertEquals(ModelAndView.withView("message"), modelAndView);
    }

    @Test
    public void handleAndReturnDefaultView() {
        ModelAndView modelAndView = baseExceptionHandler.handle(new SeanceCreateException("message"));

        assertEquals(ModelAndView.withView("default view"), modelAndView);
    }
}