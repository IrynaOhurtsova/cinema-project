package com.cinema.project.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@RequiredArgsConstructor
public class ConfigLoader {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T loadConfig(String configPath, Class<T> configClass) {
        InputStream resourceAsStream = configClass.getClassLoader().getResourceAsStream(configPath);
        return objectMapper.readValue(resourceAsStream, configClass);
    }
}
