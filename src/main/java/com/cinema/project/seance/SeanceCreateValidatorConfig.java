package com.cinema.project.seance;

import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@PropertySource("classpath:validatorseanceconfig.properties")
@Value
public class SeanceCreateValidatorConfig {

    @org.springframework.beans.factory.annotation.Value("${maxSeatingCapacity}")
    Integer maxSeatingCapacity;
    @org.springframework.beans.factory.annotation.Value("#{T(java.time.LocalTime).parse('${minTimeSeance}')}")
    LocalTime minTimeSeance;
    @org.springframework.beans.factory.annotation.Value("#{T(java.time.LocalTime).parse('${maxTimeSeance}')}")
    LocalTime maxTimeSeance;
}
