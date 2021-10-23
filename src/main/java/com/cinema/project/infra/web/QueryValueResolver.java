package com.cinema.project.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryValueResolver {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T getObject(HttpServletRequest request, Class<T> tClass) {
        Map<String, String> parameterMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String parameter = request.getParameter(name);
            parameterMap.put(name, parameter);
        }
        return objectMapper.convertValue(parameterMap, tClass);
    }
}
