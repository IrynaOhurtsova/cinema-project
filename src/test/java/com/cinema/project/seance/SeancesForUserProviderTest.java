package com.cinema.project.seance;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeancesForUserProviderTest {

    @Mock
    private Map<UserRole, ModelAndView> modelByRole;

    private final ModelAndView defaultModelAndView = ModelAndView.withView("default model and view");
    private SeancesForUserProvider seancesForUserProvider;

    @Before
    public void init() {
        seancesForUserProvider = new SeancesForUserProvider(modelByRole, defaultModelAndView);
    }

    @Test
    public void getModelAmdViewForUser() {
        ModelAndView expected = ModelAndView.withView("model and view");
        User expectedUser = new User();
        expectedUser.setRole(UserRole.CLIENT);

        when(modelByRole.get(expectedUser.getRole())).thenReturn(expected);

        ModelAndView result = seancesForUserProvider.getModelAmdViewForUser(expectedUser);

        assertEquals(expected, result);
    }

    @Test
    public void getModelAmdViewForUserWithoutRole() {
        User expectedUser = new User();

        when(modelByRole.get(expectedUser.getRole())).thenAnswer(invocation -> Optional.empty());

        ModelAndView result = seancesForUserProvider.getModelAmdViewForUser(expectedUser);

        assertEquals(defaultModelAndView,result);
    }
}