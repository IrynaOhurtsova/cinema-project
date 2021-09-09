package com.cinema.project.movie;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Map<Long, Movie> getMoviesById(List<Long> ids, Locale locale) {
        return movieRepository.getMoviesById(ids, locale)
                .stream()
                .collect(Collectors.toMap(Movie::getId, Function.identity()));
    }

    public Movie getMovieByTitle(String title, Locale locale) throws MovieNotFoundException {
        return movieRepository.getMovieByTitle(title, locale)
                .orElseThrow(() -> new MovieNotFoundException("wrong title of movie"));
    }
}
