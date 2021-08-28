package com.cinema.project.user;

public class UserLoginRequestDtoToClientMapper {

    public User map(UserLoginRequestDto userLoginRequestDto) {
        return User.builder()
                .login(userLoginRequestDto.getLogin())
                .password(userLoginRequestDto.getPassword())
                .role(UserRole.CLIENT)
                .build();
    }
}
