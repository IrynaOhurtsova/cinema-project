package com.cinema.project.config;

import com.cinema.project.infra.auth.AuthConfiguration;
import com.cinema.project.infra.web.listener.LocaleSessionListener;
import com.cinema.project.infra.web.listener.LocaleSessionListenerConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.cinema.project.config");
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/cinema/*");

        LocaleSessionListenerConfiguration localeSessionListenerConfiguration = new LocaleSessionListenerConfiguration();
        LocaleSessionListener localeSessionListener = localeSessionListenerConfiguration.localeSessionListener();
        servletContext.addListener(localeSessionListener);

        FilterRegistration.Dynamic auth = servletContext.addFilter("auth", new AuthConfiguration().authorizationFilter());
        auth.addMappingForUrlPatterns(null, false, "/*");
    }
}
