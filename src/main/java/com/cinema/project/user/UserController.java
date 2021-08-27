package com.cinema.project.user;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QueryValueResolver queryValueResolver;

    public ModelAndView login(HttpServletRequest request) {
        UserLoginRequestDto loginRequestDto = queryValueResolver.getObject(request, UserLoginRequestDto.class);
        User user = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        ModelAndView modelAndView = ModelAndView.withView("/home.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
