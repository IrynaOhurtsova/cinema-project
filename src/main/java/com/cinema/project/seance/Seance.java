package com.cinema.project.seance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class Seance {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long movieId;
    private Double price;
    private Integer seatingCapacity;
}
