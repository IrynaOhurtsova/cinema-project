package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;
import lombok.Value;

import java.util.function.Function;
import java.util.function.Predicate;

@Value
public class ExceptionHandlerFunctionHolder {

    Predicate<Exception> predicate;
    Function<Exception, ModelAndView> handler;

}
