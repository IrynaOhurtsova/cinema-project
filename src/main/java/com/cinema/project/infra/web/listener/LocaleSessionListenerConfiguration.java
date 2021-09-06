package com.cinema.project.infra.web.listener;

import java.util.Arrays;
import java.util.Locale;

public class LocaleSessionListenerConfiguration {

    public LocaleSessionListener localeSessionListener() {
        Locale en = new Locale("en");
        Locale uk = new Locale("uk");
        return new LocaleSessionListener(Arrays.asList(en, uk), en);
    }

}
