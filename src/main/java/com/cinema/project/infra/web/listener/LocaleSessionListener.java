package com.cinema.project.infra.web.listener;

import lombok.RequiredArgsConstructor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

@RequiredArgsConstructor
public class LocaleSessionListener implements HttpSessionListener {

    private final List<Locale> locales;
    private final Locale selectedLocale;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("locales", locales);
        session.setAttribute("selectedLocale", selectedLocale);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
