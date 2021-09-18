package com.cinema.project.infra.web;

import com.cinema.project.infra.web.request.RequestHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.infra.web.response.ResponseHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FrontServletTest {

    @Mock
    private RequestHandler requestHandler;
    @Mock
    private ResponseHandler<ModelAndView> responseHandler;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @InjectMocks
    private FrontServlet frontServlet;

    @Test
    public void service() {
        ModelAndView modelAndView = ModelAndView.withView("");

        when(requestHandler.handleRequest(httpServletRequest)).thenReturn(modelAndView);
        when(responseHandler.handle(modelAndView, httpServletRequest, httpServletResponse)).thenReturn(modelAndView);

        frontServlet.service(httpServletRequest, httpServletResponse);
    }
}