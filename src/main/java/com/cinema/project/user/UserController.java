package com.cinema.project.user;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QueryValueResolver queryValueResolver;
    private final Map<UserRole, ModelAndView> modelAndViewHome;

    public ModelAndView login(HttpServletRequest request) {
        UserLoginRequestDto loginRequestDto = queryValueResolver.getObject(request, UserLoginRequestDto.class);
        User user = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        ModelAndView modelAndView = modelAndViewHome.get(user.getRole());
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView registerUser(HttpServletRequest request) {
        UserLoginRequestDto loginRequestDto = queryValueResolver.getObject(request, UserLoginRequestDto.class);
        User client = userService.registerClient(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("user", client);
        ModelAndView modelAndView = ModelAndView.withView("/home/client.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
