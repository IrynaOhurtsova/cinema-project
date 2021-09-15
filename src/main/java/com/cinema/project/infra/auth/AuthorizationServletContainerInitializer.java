package com.cinema.project.infra.auth;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;
import java.util.logging.Logger;

public class AuthorizationServletContainerInitializer implements ServletContainerInitializer {

    private static final Logger logger = Logger.getLogger(AuthorizationServletContainerInitializer.class.getName());

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        AuthConfiguration authConfiguration = new AuthConfiguration();
        FilterRegistration.Dynamic auth = ctx.addFilter("auth", authConfiguration.authorizationFilter());
        auth.addMappingForUrlPatterns(null, false, "/*");
        logger.info("start authorization filter -->" + auth);
    }
}
