package com.cinema.project.config;

import com.cinema.project.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserControllerConfig {

    @Bean
    public Map<UserRole, String> modelAndViewHome() {
        Map<UserRole, String> modelAndViewHome = new HashMap<>();
        modelAndViewHome.put(UserRole.CLIENT, "/home/client.jsp");
        modelAndViewHome.put(UserRole.ADMIN, "/home/admin.jsp");
        return modelAndViewHome;
    }

}
