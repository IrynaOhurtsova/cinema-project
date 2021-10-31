package com.cinema.project.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Map<UserRole, String> modelAndViewHome;

    @GetMapping("/change/language")
    public String changeLocale(@RequestParam(name = "view") String view, @RequestParam(name = "selectedLocale") String selectedLocale, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("selectedLocale", new Locale(selectedLocale));
        return "redirect:" + view;
    }

    @PostMapping("/login")
    public String login(UserLoginRequestDto loginRequestDto, HttpServletRequest request) {
        User user = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession(false);
        session.setAttribute("user", user);
        return "redirect:" + modelAndViewHome.get(user.getRole());
    }

    @PostMapping("/register")
    public String registerUser(UserLoginRequestDto loginRequestDto, HttpServletRequest request) {
        User client = userService.registerClient(loginRequestDto);
        HttpSession session = request.getSession(false);
        session.setAttribute("user", client);
        return "redirect:" + modelAndViewHome.get(UserRole.CLIENT);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
