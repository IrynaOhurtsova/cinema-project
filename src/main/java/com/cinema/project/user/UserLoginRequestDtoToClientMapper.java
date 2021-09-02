package com.cinema.project.user;

public class UserLoginRequestDtoToClientMapper {

    public User map(UserLoginRequestDto userLoginRequestDto) {
        User client = new User();
        client.setLogin(userLoginRequestDto.getLogin());
        client.setPassword(userLoginRequestDto.getPassword());
        client.setRole(UserRole.CLIENT);
        return client;
    }
}
