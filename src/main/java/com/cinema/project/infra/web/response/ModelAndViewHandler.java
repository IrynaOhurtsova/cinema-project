package com.cinema.project.infra.web.response;

import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ModelAndViewHandler implements ResponseHandler<ModelAndView> {

    @SneakyThrows
    @Override
    public ModelAndView handle(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        if (modelAndView.isRedirect()) {
            response.sendRedirect(request.getContextPath() + modelAndView.getView());
            return modelAndView;
        }
        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(modelAndView.getView());
        fillAttributes(request, modelAndView);
        requestDispatcher.forward(request, response);
        return modelAndView;
    }

    private void fillAttributes(HttpServletRequest request, ModelAndView modelAndView) {
        Map<String, Object> attributes = modelAndView.getAttributes();
        attributes.forEach(request::setAttribute);
    }
}
