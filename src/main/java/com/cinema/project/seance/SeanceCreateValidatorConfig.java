package com.cinema.project.seance;

import lombok.Value;

import java.time.LocalTime;

@Value
public class SeanceCreateValidatorConfig {

    Integer maxSeatingCapacity;
    LocalTime minTimeSeance;
    LocalTime maxTimeSeance;
}
