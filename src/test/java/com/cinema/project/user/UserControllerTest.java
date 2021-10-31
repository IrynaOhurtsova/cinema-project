//package com.cinema.project.user;
//
//import com.cinema.project.infra.web.QueryValueResolver;
//import com.cinema.project.infra.web.response.ModelAndView;
//import liquibase.pro.packaged.M;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import java.util.Locale;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserControllerTest {
//
//    @Mock
//    private UserService userService;
//    @Mock
//    private QueryValueResolver queryValueResolver;
//    @Mock
//    private Map<UserRole, ModelAndView> modelAndViewHome;
//    @Mock
//    private HttpServletRequest httpServletRequest;
//    @Mock
//    private HttpSession session;
//    @InjectMocks
//    private UserController userController;
//
//    private final UserLoginRequestDto userDto = new UserLoginRequestDto();
//    private final User userWithoutId = new User();
//    private final User user = new User(1L, "login", "password", UserRole.CLIENT);
//
//    @Before
//    public void beforeEachTest() {
//        userWithoutId.setLogin(user.getLogin());
//        userWithoutId.setPassword(user.getPassword());
//        userWithoutId.setRole(user.getRole());
//
//        userDto.setLogin(user.getLogin());
//        userDto.setPassword(user.getPassword());
//    }
//
//    @Test
//    public void changeLocale() {
//        ModelAndView modelAndView = new ModelAndView("", true);
//
//        when(httpServletRequest.getParameter("selectedLocale")).thenReturn("en");
//        when(httpServletRequest.getParameter("view")).thenReturn("");
//        when(httpServletRequest.getSession(false)).thenReturn(session);
//
//        assertEquals(modelAndView, userController.changeLocale(httpServletRequest));
//
//        verify(session, times(1)).setAttribute(anyString(), anyObject());
//    }
//
//    @Test
//    public void login() {
//        ModelAndView modelAndView = new ModelAndView("", true);
//
//        when(queryValueResolver.getObject(httpServletRequest, UserLoginRequestDto.class)).thenReturn(userDto);
//        when(userService.loginUser(userDto)).thenReturn(user);
//        when(httpServletRequest.getSession(false)).thenReturn(session);
//        when(modelAndViewHome.get(user.getRole())).thenReturn(modelAndView);
//
//        assertEquals(modelAndView, userController.login(httpServletRequest));
//
//        verify(session, times(1)).setAttribute("user", user);
//    }
//
//    @Test
//    public void registerUser() {
//        ModelAndView modelAndView = new ModelAndView("/home/client.jsp", true);
//
//        when(queryValueResolver.getObject(httpServletRequest, UserLoginRequestDto.class)).thenReturn(userDto);
//        when(userService.registerClient(userDto)).thenReturn(user);
//        when(httpServletRequest.getSession(false)).thenReturn(session);
//
//        assertEquals(modelAndView, userController.registerUser(httpServletRequest));
//
//        verify(session, times(1)).setAttribute("user", user);
//    }
//
//    @Test
//    public void logout() {
//        when(httpServletRequest.getSession()).thenReturn(session);
//
//        assertEquals(new ModelAndView("", true), userController.logout(httpServletRequest));
//
//        verify(session, times(1)).removeAttribute("user");
//    }
//}