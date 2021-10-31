package com.cinema.project.seance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeanceCreateValidatorConfig {

    @org.springframework.beans.factory.annotation.Value("${maxSeatingCapacity}")
    Integer maxSeatingCapacity;
    @org.springframework.beans.factory.annotation.Value("#{T(java.time.LocalTime).parse('${minTimeSeance}')}")
    LocalTime minTimeSeance;
    @org.springframework.beans.factory.annotation.Value("#{T(java.time.LocalTime).parse('${maxTimeSeance}')}")
    LocalTime maxTimeSeance;
}
