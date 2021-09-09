package com.cinema.project.infra.auth;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;

public class AuthorizationServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        AuthConfiguration authConfiguration = new AuthConfiguration();
        FilterRegistration.Dynamic auth = ctx.addFilter("auth", authConfiguration.authorizationFilter());
        auth.addMappingForUrlPatterns(null, false, "/*");
    }
}
