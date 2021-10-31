package com.cinema.project.seance;

import com.cinema.project.user.User;
import com.cinema.project.user.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SeancesForUserProvider {

    private final Map<UserRole, String> modelByRole;
    private final String defaultModel;

    public String getModelAndViewForUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getRole)
                .map(modelByRole::get)
                .orElse(defaultModel);
    }
}
