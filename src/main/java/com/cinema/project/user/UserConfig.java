package com.cinema.project.user;

import com.cinema.project.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:/property/validator/clientregistervalidator.properties")
public class UserConfig {

    @Bean
    public Map<UserRole, String> modelAndViewHome() {
        Map<UserRole, String> modelAndViewHome = new HashMap<>();
        modelAndViewHome.put(UserRole.CLIENT, "/home/client.jsp");
        modelAndViewHome.put(UserRole.ADMIN, "/home/admin.jsp");
        return modelAndViewHome;
    }

}
