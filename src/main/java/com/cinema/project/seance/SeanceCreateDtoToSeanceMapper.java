package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeanceCreateDtoToSeanceMapper {

    private final MovieService movieService;

    public Seance map(SeanceCreateDto seanceCreateDto) {
        Movie movie = movieService.getMovieByTitle(seanceCreateDto.getTitle());
        return Seance.builder()
                .date(seanceCreateDto.getDate())
                .time(seanceCreateDto.getTime())
                .movieId(movie.getId())
                .price(seanceCreateDto.getPrice())
                .seatingCapacity(seanceCreateDto.getSeatingCapacity())
                .build();
    }
}
