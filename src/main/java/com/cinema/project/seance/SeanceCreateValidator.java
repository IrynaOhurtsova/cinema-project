package com.cinema.project.seance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Optional;

@AllArgsConstructor
@Data
@Builder
public class SeanceCreateValidator {

    private SeanceRepository seanceRepository;
    private Integer maxSeatingCapacity;
    private LocalTime minTimeSeance;
    private LocalTime maxTimeSeance;

    public Seance validate(Seance seance) {
        if (!checkSeanceTime(seance)) {
            throw new SeanceCreateException("wrong time, time can be from 9:00 to 22:00");
        }
        if (checkSeanceDateAndTime(seance).isPresent()) {
            throw new SeanceCreateException("seance with this date and time exists, choose vacant date and time");
        }
        if (seance.getSeatingCapacity() > maxSeatingCapacity) {
            throw new SeanceCreateException("wrong seating capacity, seating capacity can be to 300 people");
        }
        return seance;
    }

    private boolean checkSeanceTime(Seance seance) {
        return seance.getTime().compareTo(minTimeSeance) >= 0
                && seance.getTime().compareTo(maxTimeSeance) <= 0;
    }

    private Optional<Seance> checkSeanceDateAndTime(Seance seance) {
        return seanceRepository.findSeanceByDateAndTime(seance.getDate(), seance.getTime());
    }
}
