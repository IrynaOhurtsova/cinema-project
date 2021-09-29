package com.cinema.project.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientRegisterValidatorTest {

    @Mock
    private UserRepository userRepository;

    private ClientRegisterValidator clientRegisterValidator;

    private final User user = new User(1L, "ivanov", "ivanoV11", UserRole.CLIENT);

    @Before
    public void init() {
        String loginRegex = "[a-zA-Z]{4,20}";
        String passwordRegex = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}";
        clientRegisterValidator = new ClientRegisterValidator(userRepository, loginRegex, passwordRegex);
    }

    @Test
    public void validate() {
        when(userRepository.getUserByLogin(user.getLogin())).thenReturn(Optional.empty());

        assertEquals(user, clientRegisterValidator.validate(user));
    }

    @Test(expected = UserLoginException.class)
    public void validateThrowsException() {
        when(userRepository.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));

        clientRegisterValidator.validate(user);
    }
}