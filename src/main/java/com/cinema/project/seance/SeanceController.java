package com.cinema.project.seance;

import com.cinema.project.infra.web.QueryValueResolver;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/seance")
@RequiredArgsConstructor
public class SeanceController {

    private final SeanceService seanceService;
    private final QueryValueResolver queryValueResolver;

    @GetMapping("/create")
    public String createSeance(@RequestParam Map<String, String> allParams, @SessionAttribute(name = "selectedLocale") Locale selectedLocale) {
        SeanceCreateDto seanceCreateDto = queryValueResolver.getObject(allParams, SeanceCreateDto.class);
        seanceService.createSeance(seanceCreateDto, selectedLocale);
        return "redirect:/cinema/mainpage";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "seanceId") String id) {
        seanceService.deleteSeanceById(Long.valueOf(id));
        return "redirect:/cinema/mainpage";
    }

}
