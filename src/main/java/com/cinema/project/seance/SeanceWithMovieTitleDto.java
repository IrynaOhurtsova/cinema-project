package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class SeanceWithMovieTitleDto {

    Long id;
    LocalDate date;
    LocalTime time;
    Double price;
    Integer seatingCapacity;
    Integer freePlaces;
    Movie movie;

    public SeanceWithMovieTitleDto(Seance seance, Movie movie) {
        id = seance.getId();
        date = seance.getDate();
        time = seance.getTime();
        price = seance.getPrice();
        seatingCapacity = seance.getSeatingCapacity();
        freePlaces = seance.getFreePlaces();
        this.movie = movie;
    }
}
