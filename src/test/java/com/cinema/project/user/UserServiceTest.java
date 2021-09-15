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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserLoginRequestDtoToClientMapper userLoginRequestDtoToClientMapper;
    @Mock
    private ClientRegisterValidator clientRegisterValidator;
    @InjectMocks
    private UserService userService;

    private final UserLoginRequestDto userDto = new UserLoginRequestDto();
    private final User userWithoutId = new User();
    private final User user = new User(1L, "login", "password", UserRole.CLIENT);

    @Before
    public void beforeEachTest() {
        userWithoutId.setLogin(user.getLogin());
        userWithoutId.setPassword(user.getPassword());
        userWithoutId.setRole(user.getRole());

        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
    }

    @Test
    public void loginUser() {
        when(userRepository.getUserByLogin(userDto.getLogin())).thenReturn(Optional.of(user));

        assertEquals(user, userService.loginUser(userDto));
    }

    @Test(expected = UserLoginException.class)
    public void loginUnregisteredUser() {
        when(userRepository.getUserByLogin(userDto.getLogin())).thenReturn(Optional.empty());

        userService.loginUser(userDto);
    }

    @Test
    public void registerClient() {
        when(userLoginRequestDtoToClientMapper.map(userDto)).thenReturn(userWithoutId);
        when(clientRegisterValidator.validate(userWithoutId)).thenReturn(userWithoutId);
        when(userRepository.registerClient(userWithoutId)).thenReturn(user);

        assertEquals(user, userService.registerClient(userDto));
    }
}