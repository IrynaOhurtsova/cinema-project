package com.cinema.project;

import com.cinema.project.infra.web.listener.LocaleSessionListener;
import com.cinema.project.infra.web.listener.LocaleSessionListenerConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.cinema.project.config");
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/cinema/*");
        LocaleSessionListenerConfiguration localeSessionListenerConfiguration = new LocaleSessionListenerConfiguration();
        LocaleSessionListener localeSessionListener = localeSessionListenerConfiguration.localeSessionListener();
        servletContext.addListener(localeSessionListener);
    }
}
