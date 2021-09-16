package com.cinema.project.SeanceAndMovie;

import com.cinema.project.movie.Movie;
import com.cinema.project.seance.Seance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeanceAndMovie {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Double price;
    private Integer seatingCapacity;
    private Integer freePlaces;
    private Movie movie;

    public SeanceAndMovie(Seance seance, Movie movie) {
        id = seance.getId();
        date = seance.getDate();
        time = seance.getTime();
        price = seance.getPrice();
        seatingCapacity = seance.getSeatingCapacity();
        freePlaces = seance.getFreePlaces();
        this.movie = movie;
    }
}
