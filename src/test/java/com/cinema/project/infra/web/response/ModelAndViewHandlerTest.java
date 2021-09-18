package com.cinema.project.infra.web.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ModelAndViewHandlerTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @InjectMocks
    private ModelAndViewHandler modelAndViewHandler;

    @Test
    public void handleWithRedirect() {
        ModelAndView expected = new ModelAndView("", true);

        when(httpServletRequest.getContextPath()).thenReturn("");

        assertEquals(expected, modelAndViewHandler.handle(expected, httpServletRequest, httpServletResponse));

    }

    @Test
    public void handleWithForward() throws Exception {
        ModelAndView expected = ModelAndView.withView("");
        expected.addAttribute("", Locale.CANADA);

        when(httpServletRequest.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(expected.getView())).thenReturn(requestDispatcher);


        assertEquals(expected, modelAndViewHandler.handle(expected, httpServletRequest, httpServletResponse));

        verify(httpServletRequest, times(1)).setAttribute(anyString(), anyObject());
        verify(requestDispatcher, times(1)).forward(httpServletRequest, httpServletResponse);

    }
}