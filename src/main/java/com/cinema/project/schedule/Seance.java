package com.cinema.project.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
public class Seance {

    private Long id;
    private Date date;
    private Time time;
    private Long movieId;
    private Double price;
    private Integer freePlaces;
    private String movieTitle;
}
