package com.cinema.project.config;

import com.cinema.project.infra.web.response.ModelAndView;
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
    public Map<UserRole, ModelAndView> modelAndViewHome() {
        Map<UserRole, ModelAndView> modelAndViewHome = new HashMap<>();
        modelAndViewHome.put(UserRole.CLIENT, ModelAndView.withView("/home/client.jsp"));
        modelAndViewHome.put(UserRole.ADMIN, ModelAndView.withView("/home/admin.jsp"));
        return modelAndViewHome;
    }

    @Bean
    public Map<UserRole, ModelAndView> paginationViewMap() {
        Map<UserRole, ModelAndView> paginationViewMap = new HashMap<>();
        paginationViewMap.put(UserRole.CLIENT, ModelAndView.withView("/pages/client.jsp"));
        paginationViewMap.put(UserRole.ADMIN, ModelAndView.withView("/pages/admin.jsp"));
        return paginationViewMap;
    }

    @Bean
    public Map<UserRole, ModelAndView> mainPageViewMap() {
        Map<UserRole, ModelAndView> mainPageViewMap = new HashMap<>();
        mainPageViewMap.put(UserRole.CLIENT, ModelAndView.withView("/mainpageforclient.jsp"));
        mainPageViewMap.put(UserRole.ADMIN, ModelAndView.withView("/mainpageforadmin.jsp"));
        return mainPageViewMap;
    }

}
