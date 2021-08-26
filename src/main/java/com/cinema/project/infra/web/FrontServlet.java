package com.cinema.project.infra.web;

import com.cinema.project.infra.web.request.RequestHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.infra.web.response.ResponseHandler;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class FrontServlet extends HttpServlet {

    private final RequestHandler requestHandler;
    private final ResponseHandler<ModelAndView> responseHandler;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = requestHandler.handleRequest(req);
        responseHandler.handle(modelAndView, req, resp);
    }

}
