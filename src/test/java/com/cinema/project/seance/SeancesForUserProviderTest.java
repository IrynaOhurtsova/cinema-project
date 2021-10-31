package com.cinema.project.seance;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeancesForUserProviderTest {

    @Mock
    private Map<UserRole, String> modelByRole;

    private final String defaultModelAndView = "default model and view";
    private SeancesForUserProvider seancesForUserProvider;

    @Before
    public void init() {
        seancesForUserProvider = new SeancesForUserProvider(modelByRole, defaultModelAndView);
    }

    @Test
    public void getModelAmdViewForUser() {
        String expected = "model and view";
        User expectedUser = new User();
        expectedUser.setRole(UserRole.CLIENT);

        when(modelByRole.get(expectedUser.getRole())).thenReturn(expected);

        String result = seancesForUserProvider.getModelAndViewForUser(expectedUser);

        assertEquals(expected, result);
    }

    @Test
    public void getModelAmdViewForUserWithoutRole() {
        User expectedUser = new User();

        when(modelByRole.get(expectedUser.getRole())).thenAnswer(invocation -> Optional.empty());

        String result = seancesForUserProvider.getModelAndViewForUser(expectedUser);

        assertEquals(defaultModelAndView, result);
    }
}