package com.cinema.project.infra.auth;

import com.cinema.project.user.UserRole;

import java.util.Arrays;
import java.util.List;

public class AuthConfiguration {

    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(authorizationPathMatcherList());
    }

    private List<AuthorizationPathMatcher> authorizationPathMatcherList() {
        //holders
        AuthorizationPathMatcher createdSeance = new AuthorizationPathMatcher("/cinema/seance/create", UserRole.ADMIN);
        AuthorizationPathMatcher deleteSeance = new AuthorizationPathMatcher("/cinema/seance/delete", UserRole.ADMIN);
        AuthorizationPathMatcher buyTicket = new AuthorizationPathMatcher("/cinema/ticket/buy", UserRole.CLIENT);
        AuthorizationPathMatcher clientTickets = new AuthorizationPathMatcher("/cinema/ticket/mytickets", UserRole.CLIENT);
        AuthorizationPathMatcher availableClientSeances = new AuthorizationPathMatcher("/cinema/seance/available", UserRole.CLIENT);
        AuthorizationPathMatcher logout = new AuthorizationPathMatcher("/cinema/user/logout", UserRole.CLIENT, UserRole.ADMIN);
        AuthorizationPathMatcher paginationAvailableSeances = new AuthorizationPathMatcher("cinema/seance/available/page", UserRole.CLIENT);

        //general jsp
        AuthorizationPathMatcher mainPageForAdminJsp = new AuthorizationPathMatcher("/mainpageforadmin.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher mainPageForClientJsp = new AuthorizationPathMatcher("/mainpageforclient.jsp", UserRole.CLIENT);

        //home jsp
        AuthorizationPathMatcher homeClient = new AuthorizationPathMatcher("/home/client.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher homeAdmin = new AuthorizationPathMatcher("/home/admin.jsp", UserRole.ADMIN);

        //seance jsp
        AuthorizationPathMatcher availableSeancesForClient = new AuthorizationPathMatcher("/seance/available.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher createSeanceJsp = new AuthorizationPathMatcher("/seance/createnewseance.jsp", UserRole.ADMIN);

        //pages
        AuthorizationPathMatcher paginationForAdmin = new AuthorizationPathMatcher("/pages/admin.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher paginationForClient = new AuthorizationPathMatcher("/pages/client.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher paginationAvailableSeancesForClient = new AuthorizationPathMatcher("/pages/available.jsp", UserRole.CLIENT);

        //ticket jsp
        AuthorizationPathMatcher clientTicketsJsp = new AuthorizationPathMatcher("/ticket/mytickets.jsp", UserRole.CLIENT);

        //error jsp
        AuthorizationPathMatcher creatingIsImpossible = new AuthorizationPathMatcher("/error/creatingisimpossible.jsp", UserRole.ADMIN);
        AuthorizationPathMatcher movieNotFound = new AuthorizationPathMatcher("/error/movienotfound.jsp", UserRole.ADMIN, UserRole.CLIENT);
        AuthorizationPathMatcher ticketBuying = new AuthorizationPathMatcher("/error/ticketbuing.jsp", UserRole.CLIENT);
        AuthorizationPathMatcher ticketExist = new AuthorizationPathMatcher("/error/ticketexist.jsp", UserRole.CLIENT);

        return Arrays.asList(createdSeance, deleteSeance, logout, paginationAvailableSeances, buyTicket, clientTickets, availableClientSeances,
                mainPageForAdminJsp, mainPageForClientJsp, homeClient, homeAdmin, availableSeancesForClient, createSeanceJsp, clientTicketsJsp,
                creatingIsImpossible, movieNotFound, ticketBuying, ticketExist, paginationForAdmin, paginationForClient, paginationAvailableSeancesForClient);
    }
}
