package com.cinema.project.infra.auth;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {


    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private FilterChain filterChain;

    private AuthorizationFilter authorizationFilter;

    private final User client = new User(1L, "", "", UserRole.CLIENT);
    private final User admin = new User(2L, "", "", UserRole.ADMIN);

    @Before
    public void initBeforeTest() {
        authorizationFilter = new AuthorizationFilter(Collections.singletonList(new AuthorizationPathMatcher("/app", UserRole.CLIENT)));

    }

    @Test
    public void initSuccessful() throws Exception {
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getRequestURI()).thenReturn("/app");
        when(httpServletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(client);

        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void initNotSuccessful() throws Exception {
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getRequestURI()).thenReturn("/app");
        when(httpServletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(admin);
        when(httpServletRequest.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);


        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(requestDispatcher, times(1)).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    public void initWithUnregisteredPath() throws Exception {
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getRequestURI()).thenReturn("/unregistered");

        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }
}