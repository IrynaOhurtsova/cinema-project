package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final MovieService movieService;
    private final SeanceCreateValidator seanceCreateValidator;
    private final SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;

    public List<SeanceWithMovieTitleDto> getAllSeances() {
        List<Seance> seances = seanceRepository.getAllSeances();

        List<Long> moviesId = seances.stream()
                .map(Seance::getMovieId)
                .collect(Collectors.toList());
        Map<Long, Movie> moviesById = movieService.getMoviesById(moviesId);

        return seances.stream()
                .map(seance -> new SeanceWithMovieTitleDto(seance, moviesById.get(seance.getMovieId())))
                .collect(Collectors.toList());
    }

    public Seance createSeance(SeanceCreateDto seanceCreateDto) {
        Seance seance = seanceCreateDtoToSeanceMapper.map(seanceCreateDto);
        return seanceRepository.createSeance(seanceCreateValidator.validate(seance));
    }

    public Seance deleteSeanceById(Long id) {
        return seanceRepository.deleteSeanceById(seanceRepository.findSeanceByID(id)
                .orElseThrow(() -> new SeanceNotFoundException("seance doesn't exist")));
    }

    public Map<Long, SeanceWithMovieTitleDto> getSeancesByIds(List<Long> ids) {
        List<Seance> seances = seanceRepository.getSeancesByIds(ids);

        List<Long> moviesId = seances.stream()
                .map(Seance::getMovieId)
                .collect(Collectors.toList());
        Map<Long, Movie> moviesById = movieService.getMoviesById(moviesId);

        return seances.stream()
                .collect(Collectors.toMap(Seance::getId,
                        seance -> new SeanceWithMovieTitleDto(seance, moviesById.get(seance.getMovieId()))));
    }

    public Seance getSeanceById(Long id) {
        return seanceRepository.findSeanceByID(id)
                .orElseThrow(()->new SeanceNotFoundException("seance doesn't exist"));
    }

}
