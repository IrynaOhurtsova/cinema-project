package com.cinema.project.infra.web.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocaleSessionListenerTest {

    @Mock
    private HttpSessionEvent httpSessionEvent;
    @Mock
    private HttpSession session;
    @InjectMocks
    private LocaleSessionListener localeSessionListener;

    @Test
    public void sessionCreated() {
        when(httpSessionEvent.getSession()).thenReturn(session);

        localeSessionListener.sessionCreated(httpSessionEvent);

        verify(session, times(2)).setAttribute(anyString(), anyObject());
    }
}