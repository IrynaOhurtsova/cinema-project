package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SeanceCreateDtoToSeanceMapper {

    private final MovieService movieService;

    public Seance map(SeanceCreateDto seanceCreateDto, Locale locale) {
        Movie movie = movieService.getMovieByTitle(seanceCreateDto.getTitle(), locale);
        return Seance.builder()
                .date(seanceCreateDto.getDate())
                .time(seanceCreateDto.getTime())
                .movieId(movie.getId())
                .price(seanceCreateDto.getPrice())
                .seatingCapacity(seanceCreateDto.getSeatingCapacity())
                .freePlaces(seanceCreateDto.getFreePlaces())
                .build();
    }
}
