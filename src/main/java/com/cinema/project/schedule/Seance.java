package com.cinema.project.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Seance {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long movieId;
    private Double price;
    private Integer freePlaces;
}
