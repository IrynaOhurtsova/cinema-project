package com.cinema.project.seanceandmovie;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import com.cinema.project.seance.Seance;
import com.cinema.project.seance.SeanceService;
import com.cinema.project.ticket.TicketService;
import com.cinema.project.user.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SeanceAndMovieService {

    private final SeanceService seanceService;
    private final MovieService movieService;
    private final TicketService ticketService;
    private final Integer counterSeancesPerPage;

    public List<SeanceAndMovie> getAllSeances(Locale locale) {
        List<Seance> seances = seanceService.getAllSeances();

        Map<Long, Movie> moviesById = getMoviesById(seances, locale);

        return getSeancesAndMovie(moviesById, seances);
    }

    public Map<Long, SeanceAndMovie> getSeancesByIds(List<Long> ids, Locale locale) {
        List<Seance> seances = seanceService.getSeancesByIds(ids);

        Map<Long, Movie> moviesById = getMoviesById(seances, locale);

        return seances.stream()
                .collect(Collectors.toMap(Seance::getId,
                        seance -> new SeanceAndMovie(seance, moviesById.get(seance.getMovieId()))));
    }

    public List<SeanceAndMovie> getSeancesPerPage(String firstValue, Locale locale) {
        List<Seance> seancesPerPage = seanceService.getSeancesPerPage(counterSeancesPerPage, firstValue);

        Map<Long, Movie> moviesById = getMoviesById(seancesPerPage, locale);

        return getSeancesAndMovie(moviesById, seancesPerPage);
    }

    public List<SeanceAndMovie> getSeanceForUserByTickets(User user, Locale locale) {
        List<Long> seanceIds = ticketService.getSeancesIdsByTickets(user);
        Map<Long, SeanceAndMovie> seancesByIds = getSeancesByIds(seanceIds, locale);
        return new ArrayList<>(seancesByIds.values());
    }

    public List<SeanceAndMovie> getSeancesPerPageForUser(User user, String firstValue, Locale locale) {
        List<Long> ids = ticketService.getSeancesIdsByTickets(user);
        return getSeancesPerPageByIds(ids, firstValue, locale);
    }

    public Map<Integer, Integer> getPageAndFirstValue() {
        List<Seance> allSeances = seanceService.getAllSeances();
        return findPageAndFirstValue(allSeances);
    }

    public Map<Integer, Integer> getPageAndFirstValueForUser(User user) {
        List<Long> seancesIds = ticketService.getSeancesIdsByTickets(user);
        List<Seance> seancesByIds = seanceService.getSeancesByIds(seancesIds);
        return findPageAndFirstValue(seancesByIds);
    }

    private Map<Integer, Integer> findPageAndFirstValue(List<Seance> seances) {
        int countOfPage = (int) Math.ceil((double) seances.size() / counterSeancesPerPage);
        return IntStream.range(0, countOfPage)
                .boxed()
                .collect(Collectors.toMap(page -> page + 1, firstValue -> firstValue * counterSeancesPerPage));
    }

    private List<SeanceAndMovie> getSeancesPerPageByIds(List<Long> ids, String firstValue, Locale locale) {
        List<Seance> seancesPerPage = seanceService.getSeancesPerPageByIds(counterSeancesPerPage, ids, firstValue);

        Map<Long, Movie> moviesById = getMoviesById(seancesPerPage, locale);

        return getSeancesAndMovie(moviesById, seancesPerPage);
    }

    private Map<Long, Movie> getMoviesById(List<Seance> seances, Locale locale) {
        List<Long> moviesId = seances.stream()
                .map(Seance::getMovieId)
                .collect(Collectors.toList());
        return movieService.getMoviesById(moviesId, locale);
    }

    private List<SeanceAndMovie> getSeancesAndMovie(Map<Long, Movie> moviesById, List<Seance> seances) {
        return seances.stream()
                .map(seance -> new SeanceAndMovie(seance, moviesById.get(seance.getMovieId())))
                .collect(Collectors.toList());
    }


}
