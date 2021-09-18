package com.cinema.project.infra.web.request;

import com.cinema.project.infra.web.exeption.handler.ExceptionHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private ExceptionHandler exceptionHandler;

    private RequestHandler requestHandler;

    private final String requestMapping = "/request";
    private final String requestMethod = "GET";
    private final Function<HttpServletRequest, ModelAndView> controllerFunction = request -> ModelAndView.withView("response");
    private final ControllerFunctionHolder controllerFunctionHolder = new ControllerFunctionHolder(requestMapping, requestMethod, controllerFunction);
    private final List<ControllerFunctionHolder> controllerFunctionHolderList = Collections.singletonList(controllerFunctionHolder);
    private final Supplier<ModelAndView> controllerNotFoundResponseSupplier = () -> ModelAndView.withView("error");

    @Before
    public void init() {
        requestHandler = new RequestHandler(controllerFunctionHolderList, exceptionHandler, controllerNotFoundResponseSupplier);
    }

    @Test
    public void handleRequest() {
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURI()).thenReturn("/request");
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getServletPath()).thenReturn("");

        assertEquals(ModelAndView.withView("response"), requestHandler.handleRequest(httpServletRequest));
    }

    @Test
    public void handleRequestNotSuccessful() {
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURI()).thenReturn("/wrong request");
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getServletPath()).thenReturn("");

        assertEquals(ModelAndView.withView("error"), requestHandler.handleRequest(httpServletRequest));
    }
}