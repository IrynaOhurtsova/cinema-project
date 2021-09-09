package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SeanceCreateValidator {

    private final SeanceRepository seanceRepository;
    private final SeanceCreateValidatorConfig config;

    public Seance validate(Seance seance) {
        if (!checkSeanceTime(seance)) {
            throw new SeanceCreateException("wrong_time");
        }
        if (checkSeanceDateAndTime(seance).isPresent()) {
            throw new SeanceCreateException("wrong_date_and_time");
        }
        if (seance.getSeatingCapacity() > config.getMaxSeatingCapacity()) {
            throw new SeanceCreateException("wrong_seating_capacity");
        }
        if (seance.getFreePlaces() > config.getMaxSeatingCapacity()) {
            throw new SeanceCreateException("wrong_count_of_free_places");
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
