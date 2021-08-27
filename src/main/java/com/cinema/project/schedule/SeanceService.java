package com.cinema.project.schedule;

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
}
