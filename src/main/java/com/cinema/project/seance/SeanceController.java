package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/seance")
@RequiredArgsConstructor
public class SeanceController {

    private final SeanceService seanceService;

    @GetMapping("/create")
    public String createSeance(SeanceCreateDto seanceCreateDto, @SessionAttribute(name = "selectedLocale") Locale selectedLocale) {
        seanceService.createSeance(seanceCreateDto, selectedLocale);
        return "redirect:/cinema/mainpage";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "seanceId") String id) {
        seanceService.deleteSeanceById(Long.valueOf(id));
        return "redirect:/cinema/mainpage";
    }

}
