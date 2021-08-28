package com.cinema.project.movie;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie getMovieById(Long id) {
        return movieRepository.getMovieById(id)
                .orElseThrow(() -> new MovieNotFoundException());
    }

    public Map<Long, Movie> getMoviesById(List<Long> ids) {
        return movieRepository.getMoviesById(ids)
                .stream()
                .collect(Collectors.toMap(Movie::getId, Function.identity()));
    }

    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
        return movieRepository.getMovieByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException("wrong title of movie"));
    }
}
