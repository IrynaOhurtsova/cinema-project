package com.cinema.project.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryValueResolver {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T getObject(Map<String, String> allParams, Class<T> tClass) {
        return objectMapper.convertValue(allParams, tClass);
    }
}
