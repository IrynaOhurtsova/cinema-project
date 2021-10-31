package com.cinema.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class MovieRepositoryConfig {

    @Bean
    public Map<Locale, String> titleColumns() {
        Map<Locale, String> titleColumns = new HashMap<>();
        titleColumns.put(new Locale("en"), "title_en");
        titleColumns.put(new Locale("uk"), "title_uk");
        return titleColumns;
    }
}
