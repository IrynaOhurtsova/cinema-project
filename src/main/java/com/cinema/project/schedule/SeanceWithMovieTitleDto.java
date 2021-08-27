package com.cinema.project.schedule;

import com.cinema.project.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class SeanceWithMovieTitleDto {

    Long id;
    LocalDate date;
    LocalTime time;
    Double price;
    Integer freePlaces;
    Movie movie;

    public SeanceWithMovieTitleDto(Seance seance, Movie movie) {
        id = seance.getId();
        date = seance.getDate();
        time = seance.getTime();
        price = seance.getPrice();
        freePlaces = seance.getFreePlaces();
        this.movie = movie;
    }
}
