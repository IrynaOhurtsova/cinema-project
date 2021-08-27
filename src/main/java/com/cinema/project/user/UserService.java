package com.cinema.project.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loginUser(UserLoginRequestDto userLoginRequestDto) {
        return userRepository.getUserById(userLoginRequestDto.getLogin())
                .filter(user -> user.getPassword().equals(userLoginRequestDto.getPassword()))
                .orElseThrow(()->new UserLoginException("wrong login or password"));
    }

}
