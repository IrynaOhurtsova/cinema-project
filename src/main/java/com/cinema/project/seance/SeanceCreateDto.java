package com.cinema.project.seance;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SeanceCreateDto {

    LocalDate date;
    LocalTime time;
    String title;
    Double price;
    Integer seatingCapacity;

}
