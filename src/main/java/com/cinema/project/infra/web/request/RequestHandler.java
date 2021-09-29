package com.cinema.project.infra.web.request;

import com.cinema.project.infra.web.exeption.handler.ExceptionHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class RequestHandler {

    private static final Logger logger = Logger.getLogger(RequestHandler.class.getName());

    private final List<ControllerFunctionHolder> controllerFunctionHolders;
    private final ExceptionHandler exceptionHandler;
    private final Supplier<ModelAndView> controllerNotFoundResponseSupplier;

    public ModelAndView handleRequest(HttpServletRequest req) {
        String method = req.getMethod();
        String controllerPath = getControllerPath(req);
        return controllerFunctionHolders.stream()
                .filter(controllerFunctionHolder -> controllerFunctionHolder.isMatch(controllerPath, method))
                .findFirst()
                .map(ControllerFunctionHolder::getControllerFunction)
                .map(handler -> invokeController(req, handler))
                .orElseGet(controllerNotFoundResponseSupplier);
    }

    private ModelAndView invokeController(HttpServletRequest req,
                                          Function<HttpServletRequest, ModelAndView> handler) {
        try {
            return handler.apply(req);
        } catch (Exception exception) {
            logger.severe(exception.getMessage());
            return exceptionHandler.handle(exception);
        }
    }

    private String getControllerPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length() + request.getServletPath().length());
    }
}
