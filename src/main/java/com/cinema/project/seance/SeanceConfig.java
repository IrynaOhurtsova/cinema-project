package com.cinema.project.seance;

import com.cinema.project.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:/property/validator/seancecreatevalidatorconfig.properties")
public class SeanceConfig {

    @Bean
    public Map<UserRole, String> paginationViewMap() {
        Map<UserRole, String> paginationViewMap = new HashMap<>();
        paginationViewMap.put(UserRole.CLIENT, "/pages/client");
        paginationViewMap.put(UserRole.ADMIN, "/pages/admin");
        return paginationViewMap;
    }

    @Bean
    public Map<UserRole, String> mainPageViewMap() {
        Map<UserRole, String> mainPageViewMap = new HashMap<>();
        mainPageViewMap.put(UserRole.CLIENT, "/mainpageforclient");
        mainPageViewMap.put(UserRole.ADMIN, "/mainpageforadmin");
        return mainPageViewMap;
    }

    @Bean
    public SeancesForUserProvider paginationViewProvider() {
        return new SeancesForUserProvider(paginationViewMap(), "/pages/user");
    }

    @Bean
    public SeancesForUserProvider mainPageViewProvider() {
        return new SeancesForUserProvider(mainPageViewMap(), "/mainpage");
    }
}
