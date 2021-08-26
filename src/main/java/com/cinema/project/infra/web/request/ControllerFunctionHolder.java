package com.cinema.project.infra.web.request;

import com.cinema.project.infra.web.response.ModelAndView;
import lombok.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

@Value
public class ControllerFunctionHolder {

    String requestMapping;
    String requestMethod;
    Function<HttpServletRequest, ModelAndView> controllerFunction;

    public boolean isMatch(String path, String method) {
        return path.matches(requestMapping) && method.equals(requestMethod);
    }

}
