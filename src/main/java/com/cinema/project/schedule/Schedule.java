package com.cinema.project.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private List<Seance> seances;

    public boolean addSeance(Seance seance) {
        return seances.add(seance);
    }

    public void itrr() {
        for (Seance seance : seances) {
            seance.getDate();
        }
    }
}
