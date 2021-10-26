package com.cinema.project.config;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.cinema.project"})
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new JSR310Module());
        return objectMapper;
    }

    @Bean
    public Map<Locale, String> titleColumns() {
        Map<Locale, String> titleColumns = new HashMap<>();
        titleColumns.put(new Locale("en"), "title_en");
        titleColumns.put(new Locale("uk"), "title_uk");
        return titleColumns;
    }

    @Bean
    public Map<UserRole, String> modelAndViewHome() {
        Map<UserRole, String> modelAndViewHome = new HashMap<>();
        modelAndViewHome.put(UserRole.CLIENT, "/home/client");
        modelAndViewHome.put(UserRole.ADMIN, "/home/admin.");
        return modelAndViewHome;
    }

    @Bean
    public Map<UserRole, String> paginationViewMap() {
        Map<UserRole, String> paginationViewMap = new HashMap<>();
        paginationViewMap.put(UserRole.CLIENT, "/pages/client.jsp");
        paginationViewMap.put(UserRole.ADMIN, "/pages/admin.jsp");
        return paginationViewMap;
    }

    @Bean
    public Map<UserRole, String> mainPageViewMap() {
        Map<UserRole, String> mainPageViewMap = new HashMap<>();
        mainPageViewMap.put(UserRole.CLIENT, "/mainpageforclient.jsp");
        mainPageViewMap.put(UserRole.ADMIN, "/mainpageforadmin.jsp");
        return mainPageViewMap;
    }

    @Bean
    public SeancesForUserProvider paginationViewProvider() {
        return new SeancesForUserProvider(paginationViewMap(), "/pages/user");
    }

    @Bean
    SeancesForUserProvider mainPageViewProvider() {
        return new SeancesForUserProvider(mainPageViewMap(), "/mainpage");
    }
}
