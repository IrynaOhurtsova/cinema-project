package com.cinema.project.seance;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SeanceCreateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime time;
    String title;
    Double price;
    Integer seatingCapacity;
    Integer freePlaces;
}
