package com.cinema.project.seanceandmovie;

import com.cinema.project.seance.SeancesForUserProvider;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SeanceAndMovieController {

    private final SeanceAndMovieService seanceAndMovieService;
    private final SeancesForUserProvider paginationViewProvider;
    private final SeancesForUserProvider mainPageViewProvider;

    @GetMapping("/mainpage")
    public String allSeances(@SessionAttribute(name = "selectedLocale") Locale selectedLocale, @SessionAttribute(required = false, name = "user") User user, Model model) {
        List<SeanceAndMovie> seances = seanceAndMovieService.getAllSeances(selectedLocale);
        model.addAttribute("seances", seances);
        return mainPageViewProvider.getModelAndViewForUser(user);
    }

    @GetMapping("/seance/page")
    public String pagination(@RequestParam(name = "value") String firstValue, @SessionAttribute(name = "selectedLocale") Locale selectedLocale, @SessionAttribute(required = false, name = "user") User user, Model model) {
        Map<Integer, Integer> pageAndFirstValue = seanceAndMovieService.getPageAndFirstValue();
        List<SeanceAndMovie> seancesPerPage = seanceAndMovieService.getSeancesPerPage(firstValue, selectedLocale);

        model.addAttribute("pageAndFirstValue", pageAndFirstValue);
        model.addAttribute("seances", seancesPerPage);
        return paginationViewProvider.getModelAndViewForUser(user);
    }


    @GetMapping("/seance/available/page")
    public String paginationForAvailableSeances(@RequestParam(name = "value") String firstValue, @SessionAttribute(name = "selectedLocale") Locale selectedLocale, @SessionAttribute(name = "user") User user, Model model) {
        Map<Integer, Integer> pageAndFirstValue = seanceAndMovieService.getPageAndFirstValueForUser(user);
        List<SeanceAndMovie> seancesPerPage = seanceAndMovieService.getSeancesPerPageForUser(user, firstValue, selectedLocale);

        model.addAttribute("pageAndFirstValue", pageAndFirstValue);
        model.addAttribute("seances", seancesPerPage);
        return "/pages/available";
    }

    @GetMapping("/seance/available")
    public String getSeancesForUserByTickets(@SessionAttribute(name = "selectedLocale") Locale selectedLocale, @SessionAttribute(name = "user") User user, Model model) {
        List<SeanceAndMovie> seances = seanceAndMovieService.getSeanceForUserByTickets(user, selectedLocale);
        model.addAttribute("seances", seances);
        return "/seance/available";
    }
}
