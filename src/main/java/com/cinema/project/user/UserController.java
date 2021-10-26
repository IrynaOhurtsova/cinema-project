package com.cinema.project.user;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

@Controller
@SessionAttributes(names = "selectedLocale")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QueryValueResolver queryValueResolver;
    private final Map<UserRole, ModelAndView> modelAndViewHome;

    @GetMapping("/user/change/language")
    public String changeLocale(@RequestParam(name = "view") String view) {
        return "redirect:" + view;
    }

    @ModelAttribute("selectedLocale")
    public Locale selectedLocale(@RequestParam(name = "selectedLocale") String selectedLocale) {
        return new Locale(selectedLocale);
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

    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new ModelAndView("", true);
    }
}
