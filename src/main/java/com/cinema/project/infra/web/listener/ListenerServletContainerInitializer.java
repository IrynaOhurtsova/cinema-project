package com.cinema.project.infra.web.listener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;

public class ListenerServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        LocaleSessionListenerConfiguration localeSessionListenerConfiguration = new LocaleSessionListenerConfiguration();
        ctx.addListener(localeSessionListenerConfiguration.localeSessionListener());
    }
}
