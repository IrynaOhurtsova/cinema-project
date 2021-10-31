package com.cinema.project.config;

import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SeanceForUserProviderConfig {

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
