package com.cinema.project.infra.web.listener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;
import java.util.logging.Logger;

public class ListenerServletContainerInitializer implements ServletContainerInitializer {

    private static final Logger logger = Logger.getLogger(ListenerServletContainerInitializer.class.getName());

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        LocaleSessionListenerConfiguration localeSessionListenerConfiguration = new LocaleSessionListenerConfiguration();
        LocaleSessionListener localeSessionListener = localeSessionListenerConfiguration.localeSessionListener();
        ctx.addListener(localeSessionListener);
        logger.info("start session listener --> " + localeSessionListener);
    }
}
