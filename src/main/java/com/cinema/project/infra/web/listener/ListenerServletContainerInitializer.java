package com.cinema.project.infra.web.listener;

import org.apache.catalina.SessionListener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

public class ListenerServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        LocaleSessionListenerConfiguration localeSessionListenerConfiguration = new LocaleSessionListenerConfiguration();
        ctx.addListener(localeSessionListenerConfiguration.localeSessionListener());
    }
}
