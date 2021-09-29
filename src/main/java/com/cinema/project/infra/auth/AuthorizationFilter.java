package com.cinema.project.infra.auth;

import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class AuthorizationFilter implements Filter {

    private final List<AuthorizationPathMatcher> pathMatchers;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String pathWithoutContext = getPathWithoutContext(httpServletRequest);
        Boolean hasAccess = pathMatchers.stream()
                .filter(authorizationPathMatcher -> authorizationPathMatcher.pathMatch(pathWithoutContext))
                .findFirst()
                .map(authorizationPathMatcher -> hasRole(authorizationPathMatcher, httpServletRequest))
                .orElse(true);

        if (hasAccess) {
            chain.doFilter(request, response);
            return;
        }
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/error/forbidden.jsp");
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
    }

    private String getPathWithoutContext(HttpServletRequest httpServletRequest) {
        int contextPathLength = httpServletRequest.getContextPath().length();
        return httpServletRequest.getRequestURI().substring(contextPathLength);
    }

    private boolean hasRole(AuthorizationPathMatcher authorizationPathMatcher, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        return session != null && authorizationPathMatcher.hasRole((User) session.getAttribute("user"));

    }
}
