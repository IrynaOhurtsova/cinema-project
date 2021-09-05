package com.cinema.project.infra.auth;

import com.cinema.project.user.UserRole;

import java.util.Arrays;
import java.util.List;

public class AuthConfiguration {

    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(authorizationPathMatcherList());
    }

    private List<AuthorizationPathMatcher> authorizationPathMatcherList() {
        //holders
        AuthorizationPathMatcher createdSeance = new AuthorizationPathMatcher("/cinema/seance/create", UserRole.ADMIN);
        AuthorizationPathMatcher deleteSeance = new AuthorizationPathMatcher("/cinema/seance/delete", UserRole.ADMIN);
        AuthorizationPathMatcher mainPageForAdmin = new AuthorizationPathMatcher("/cinema/mainpageforadmin", UserRole.ADMIN);
        AuthorizationPathMatcher mainPageForClient = new AuthorizationPathMatcher("/cinema/mainpageforclient", UserRole.CLIENT);
        AuthorizationPathMatcher buyTicket = new AuthorizationPathMatcher("/cinema/ticket/buy", UserRole.CLIENT);
        AuthorizationPathMatcher clientTickets = new AuthorizationPathMatcher("/cinema/ticket/mytickets", UserRole.CLIENT);
        AuthorizationPathMatcher availableClientSeances = new AuthorizationPathMatcher("/cinema/seance/available", UserRole.CLIENT);

        //general jsp
        AuthorizationPathMatcher mainPageForAdminJsp = new AuthorizationPathMatcher("/mainpageforadmin.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher mainPageForClientJsp = new AuthorizationPathMatcher("/mainpageforclient.jsp", UserRole.CLIENT);

        //home jsp
        AuthorizationPathMatcher homeClient = new AuthorizationPathMatcher("/home/client.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher homeAdmin = new AuthorizationPathMatcher("/home/admin.jsp", UserRole.ADMIN);

        //seance jsp
        AuthorizationPathMatcher createdSeanceJsp = new AuthorizationPathMatcher("/seance/createdseance.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher createSeanceJsp = new AuthorizationPathMatcher("/seance/createnewseance.jsp", UserRole.ADMIN);

        //ticket jsp
        AuthorizationPathMatcher buyingHasDone = new AuthorizationPathMatcher("/ticket/buyinghasdone.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher clientTicketsJsp = new AuthorizationPathMatcher("/ticket/mytickets.jsp", UserRole.CLIENT);

        //error jsp
        AuthorizationPathMatcher creatingIsImpossible = new AuthorizationPathMatcher("/error/creatingisimpossible.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher movieNotFound = new AuthorizationPathMatcher("/error/movienotfound.jsp", UserRole.ADMIN, UserRole.CLIENT);
        AuthorizationPathMatcher ticketBuying = new AuthorizationPathMatcher("/error/ticketbuing.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher ticketExist = new AuthorizationPathMatcher("/error/ticketexist.jsp", UserRole.CLIENT);

        return Arrays.asList(createdSeance, deleteSeance, mainPageForAdmin, mainPageForClient, buyTicket, clientTickets, availableClientSeances,
                mainPageForAdminJsp, mainPageForClientJsp, homeClient, homeAdmin, createdSeanceJsp, createSeanceJsp, buyingHasDone, clientTicketsJsp,
                creatingIsImpossible, movieNotFound, ticketBuying, ticketExist);
    }
}
