package com.cinema.project.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loginUser(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(()->new RuntimeException("User by id " + id + " not found"));
    }

}
