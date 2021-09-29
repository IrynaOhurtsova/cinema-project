package com.cinema.project.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRegisterValidator {

    private final UserRepository userRepository;
    private final String loginRegex;
    private final String passwordRegex;

    public User validate(User user) {
        if (!matchLogin(user)) {
            throw new UserLoginException("invalid_login");
        }
        if (!matchPassword(user)) {
            throw new UserLoginException("invalid_password");
        }
        if (checkLogin(user)) {
            throw new UserLoginException("user_already_exist");
        }
        return user;
    }

    private boolean checkLogin(User user) {
        return userRepository.getUserByLogin(user.getLogin()).isPresent();
    }

    private boolean matchLogin(User user) {
        return user.getLogin().matches(loginRegex);
    }

    private boolean matchPassword(User user) {
        return user.getPassword().matches(passwordRegex);
    }
}
