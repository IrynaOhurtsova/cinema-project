package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final MovieService movieService;
    private final SeanceCreateValidator seanceCreateValidator;
    private final SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;
    private final Integer counterSeancesPerPage;

    public List<SeanceWithMovieTitleDto> getAllSeances(Locale locale) {
        List<Seance> seances = seanceRepository.getAllSeances();

        Map<Long, Movie> moviesById = getMoviesById(seances, locale);

        return getSeancesWithMovieTitleDtoBySeanceAndMovie(moviesById, seances);
    }

    public Seance createSeance(SeanceCreateDto seanceCreateDto, Locale locale) {
        Seance seance = seanceCreateDtoToSeanceMapper.map(seanceCreateDto, locale);
        return seanceRepository.createSeance(seanceCreateValidator.validate(seance));
    }

    public Seance deleteSeanceById(Long id) {
        return seanceRepository.deleteSeanceById(seanceRepository.findSeanceByID(id)
                .orElseThrow(() -> new SeanceNotFoundException("seance doesn't exist")));
    }

    public Map<Long, SeanceWithMovieTitleDto> getSeancesByIds(List<Long> ids, Locale locale) {
        List<Seance> seances = getSeancesByIds(ids);

        Map<Long, Movie> moviesById = getMoviesById(seances, locale);

        return seances.stream()
                .collect(Collectors.toMap(Seance::getId,
                        seance -> new SeanceWithMovieTitleDto(seance, moviesById.get(seance.getMovieId()))));
    }

    public List<Seance> getSeancesByIds(List<Long> ids) {
        return seanceRepository.getSeancesByIds(ids);
    }

    public Seance getSeanceById(Long id) {
        return seanceRepository.findSeanceByID(id)
                .orElseThrow(() -> new SeanceNotFoundException("seance doesn't exist"));
    }

    public List<SeanceWithMovieTitleDto> getSeancesPerPage(String firstValue, Locale locale) {
        List<Seance> seancesPerPage = seanceRepository.getSeancesPerPage(counterSeancesPerPage, Integer.valueOf(firstValue));

        Map<Long, Movie> moviesById = getMoviesById(seancesPerPage, locale);

        return getSeancesWithMovieTitleDtoBySeanceAndMovie(moviesById, seancesPerPage);
    }

    public Map<Integer, Integer> getPageAndFirstValue() {
        List<Seance> allSeances = seanceRepository.getAllSeances();
        return findPageAndFirstValue(allSeances);
    }

    public List<SeanceWithMovieTitleDto> getSeancesPerPageByIds(List<Long> ids, String firstValue, Locale locale) {
        List<Seance> seancesPerPage = seanceRepository.getSeancesPerPageByIds(ids, counterSeancesPerPage, Integer.valueOf(firstValue));

        Map<Long, Movie> moviesById = getMoviesById(seancesPerPage, locale);

        return getSeancesWithMovieTitleDtoBySeanceAndMovie(moviesById, seancesPerPage);
    }

    public Map<Integer, Integer> findPageAndFirstValue(List<Seance> seances) {
        int countOfPage = (int) Math.ceil((double) seances.size() / counterSeancesPerPage);
        return IntStream.range(0, countOfPage)
                .boxed()
                .collect(Collectors.toMap(page -> page + 1, firstValue -> firstValue * counterSeancesPerPage));
    }

    private Map<Long, Movie> getMoviesById(List<Seance> seances, Locale locale) {
        List<Long> moviesId = seances.stream()
                .map(Seance::getMovieId)
                .collect(Collectors.toList());
        return movieService.getMoviesById(moviesId, locale);
    }

    private List<SeanceWithMovieTitleDto> getSeancesWithMovieTitleDtoBySeanceAndMovie(Map<Long, Movie> moviesById, List<Seance> seances) {
        return seances.stream()
                .map(seance -> new SeanceWithMovieTitleDto(seance, moviesById.get(seance.getMovieId())))
                .collect(Collectors.toList());
    }
}
