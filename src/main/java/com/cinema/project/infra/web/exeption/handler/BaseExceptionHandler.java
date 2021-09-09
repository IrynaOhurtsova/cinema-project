package com.cinema.project.infra.web.exeption.handler;

import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class BaseExceptionHandler implements ExceptionHandler {

    private final List<ExceptionHandlerFunctionHolder> exceptionHandlerFunctionHolders;
    private final Supplier<ModelAndView> defaultResultSupplier;

    @Override
    public ModelAndView handle(Exception exception) {
        return exceptionHandlerFunctionHolders.stream()
                .filter(holder -> holder.getPredicate().test(exception))
                .map(ExceptionHandlerFunctionHolder::getHandler)
                .findFirst()
                .map(handleFunction -> handleFunction.apply(exception))
                .orElseGet(defaultResultSupplier);
    }
}
