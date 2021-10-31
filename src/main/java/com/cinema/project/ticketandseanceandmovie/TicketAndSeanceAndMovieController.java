package com.cinema.project.ticketandseanceandmovie;

import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketAndSeanceAndMovieController {

    private final TicketAndSeanceAndMovieService ticketAndSeanceAndMovieService;

    @GetMapping("/mytickets")
    public String getAllTicketsByUserId(@SessionAttribute(name = "selectedLocale") Locale selectedLocale, @SessionAttribute(name = "user") User user, Model model) {
        List<TicketAndSeanceAndMovie> tickets = ticketAndSeanceAndMovieService.getTicketsForUser(user, selectedLocale);
        model.addAttribute("tickets", tickets);
        return "/ticket/mytickets";
    }


}
