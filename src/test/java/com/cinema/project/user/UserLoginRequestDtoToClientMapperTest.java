package com.cinema.project.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserLoginRequestDtoToClientMapperTest {

    private final UserLoginRequestDtoToClientMapper mapper = new UserLoginRequestDtoToClientMapper();

    @Test
    public void map() {
        User expected = new User();
        expected.setRole(UserRole.CLIENT);
        expected.setPassword("");
        expected.setLogin("");

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setLogin("");
        userLoginRequestDto.setPassword("");

        assertEquals(expected, mapper.map(userLoginRequestDto));
    }
}