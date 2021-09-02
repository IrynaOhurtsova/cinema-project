package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SeanceCreateValidator {

    private final SeanceRepository seanceRepository;
    private final SeanceCreateValidatorConfig config;

    public Seance validate(Seance seance) {
        if (!checkSeanceTime(seance)) {
            throw new SeanceCreateException("wrong time, time can be from 9:00 to 22:00");
        }
        if (checkSeanceDateAndTime(seance).isPresent()) {
            throw new SeanceCreateException("seance with this date and time exists, choose vacant date and time");
        }
        if (seance.getSeatingCapacity() > config.getMaxSeatingCapacity()) {
            throw new SeanceCreateException("wrong seating capacity, seating capacity can be to 300 people");
        }
        return seance;
    }

    private boolean checkSeanceTime(Seance seance) {
        return seance.getTime().compareTo(config.getMinTimeSeance()) >= 0
                && seance.getTime().compareTo(config.getMaxTimeSeance()) <= 0;
    }

    private Optional<Seance> checkSeanceDateAndTime(Seance seance) {
        return seanceRepository.findSeanceByDateAndTime(seance.getDate(), seance.getTime());
    }
}
