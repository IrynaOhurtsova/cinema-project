package com.cinema.project.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientRegisterValidator {

    private UserRepository userRepository;

    public User validate(User user) {
        if (checkLogin(user)) {
            throw new UserLoginException("user with this login already exists");
        }
        return user;
    }

    private boolean checkLogin(User user) {
        return userRepository.getUserByLogin(user.getLogin()).isPresent();
    }
}
