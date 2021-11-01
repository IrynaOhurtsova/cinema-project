package com.cinema.project.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Map<UserRole, String> modelAndViewHome;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;
    @InjectMocks
    private UserController userController;

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
    public void changeLocale() {
        String modelAndView = "redirect:/";

        when(httpServletRequest.getSession(false)).thenReturn(session);

        assertEquals(modelAndView, userController.changeLocale("/", String.valueOf(Locale.CANADA), httpServletRequest));

        verify(session, times(1)).setAttribute(anyString(), anyObject());
    }

    @Test
    public void login() {
        String modelAndView = "redirect:/";

        when(userService.loginUser(userDto)).thenReturn(user);
        when(httpServletRequest.getSession(false)).thenReturn(session);
        when(modelAndViewHome.get(user.getRole())).thenReturn("/");

        assertEquals(modelAndView, userController.login(userDto, httpServletRequest));

        verify(session, times(1)).setAttribute("user", user);
    }

    @Test
    public void registerUser() {
        String modelAndView = "redirect:/home/client.jsp";

        when(userService.registerClient(userDto)).thenReturn(user);
        when(httpServletRequest.getSession(false)).thenReturn(session);
        when(modelAndViewHome.get(user.getRole())).thenReturn("/home/client.jsp");

        assertEquals(modelAndView, userController.registerUser(userDto, httpServletRequest));

        verify(session, times(1)).setAttribute("user", user);
    }

    @Test
    public void logout() {
        assertEquals("redirect:/", userController.logout(session));

        verify(session, times(1)).removeAttribute("user");
    }
}