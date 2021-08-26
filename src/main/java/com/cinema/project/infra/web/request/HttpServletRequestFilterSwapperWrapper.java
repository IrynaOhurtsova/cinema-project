package com.cinema.project.infra.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletRequestFilterSwapperWrapper extends HttpServletRequestWrapper {

    private final String method;

    public HttpServletRequestFilterSwapperWrapper(HttpServletRequest request, String method) {
        super(request);
        this.method = method;
    }

    @Override
    public String getMethod() {
        return method;
    }
}
