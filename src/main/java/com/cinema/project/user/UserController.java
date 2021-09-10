package com.cinema.project.user;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QueryValueResolver queryValueResolver;
    private final Map<UserRole, ModelAndView> modelAndViewHome;

    public ModelAndView changeLocale(HttpServletRequest request) {
        String selectedLocale = request.getParameter("selectedLocale");
        String view = request.getParameter("view");
        Locale locale = new Locale(selectedLocale);
        HttpSession session = request.getSession(false);
        session.setAttribute("selectedLocale", locale);
        return new ModelAndView(view, true);
    }

    public ModelAndView login(HttpServletRequest request) {
        UserLoginRequestDto loginRequestDto = queryValueResolver.getObject(request, UserLoginRequestDto.class);
        User user = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession(false);
        session.setAttribute("user", user);
        ModelAndView modelAndView = modelAndViewHome.get(user.getRole());
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView registerUser(HttpServletRequest request) {
        UserLoginRequestDto loginRequestDto = queryValueResolver.getObject(request, UserLoginRequestDto.class);
        User client = userService.registerClient(loginRequestDto);
        HttpSession session = request.getSession(false);
        session.setAttribute("user", client);
        return new ModelAndView("/home/client.jsp", true);
    }

    public ModelAndView logout (HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new ModelAndView("", true);
    }
}
