package com.cinema.project.seance;

import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SeancesForUserProvider {

    private final Map<UserRole, ModelAndView> modelByRole;
    private final ModelAndView defaultModel;

    public ModelAndView getModelAmdViewForUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getRole)
                .map(modelByRole::get)
                .orElse(defaultModel);
    }
}
