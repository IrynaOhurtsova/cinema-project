package com.cinema.project.ticket;

import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/ticket/buy")
    public String createTicket(@RequestParam(name = "seanceId") String seanceId, @SessionAttribute(name = "user") User user) {
        ticketService.createTicket(Long.valueOf(seanceId), user);
        return "redirect:/cinema/mainpage";
    }

}
