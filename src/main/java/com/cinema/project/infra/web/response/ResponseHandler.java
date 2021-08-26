package com.cinema.project.infra.web.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler<T> {

    T handle(T responseEntity, HttpServletRequest request, HttpServletResponse response);
}
