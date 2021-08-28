package com.cinema.project.seance;

import java.time.LocalTime;

public class SeanceCreateValidatorConfig {

    public SeanceCreateValidator seanceCreateValidator() {
        return SeanceCreateValidator.builder()
                .maxSeatingCapacity(300)
                .minTimeSeance(LocalTime.of(9, 0))
                .maxTimeSeance(LocalTime.of(22, 0))
                .build();
    }
}
