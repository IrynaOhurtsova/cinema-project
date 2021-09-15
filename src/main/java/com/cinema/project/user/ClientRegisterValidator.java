package com.cinema.project.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ClientRegisterValidator {

    private final UserRepository userRepository;

    public User validate(User user) {
        if (checkLogin(user)) {
            throw new UserLoginException("user_already_exist");
        }
        return user;
    }

    private boolean checkLogin(User user) {
        return userRepository.getUserByLogin(user.getLogin()).isPresent();
    }
}
