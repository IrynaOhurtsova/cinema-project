package com.cinema.project.seance;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceCreateValidatorTest {

    @Mock
    private SeanceRepository seanceRepository;

    SeanceCreateValidatorConfig seanceCreateValidatorConfig = new SeanceCreateValidatorConfig(300, LocalTime.of(9, 0), LocalTime.of(22, 0));
    private SeanceCreateValidator seanceCreateValidator;

    @Before
    public void init() {
        seanceCreateValidator = new SeanceCreateValidator(seanceRepository, seanceCreateValidatorConfig);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void validateSeanceWithWrongTime() {
        exception.expect(SeanceCreateException.class);
        exception.expectMessage("wrong_time");

        Seance seance = Seance.builder()
                .time(LocalTime.of(1, 0))
                .build();
        seanceCreateValidator.validate(seance);
    }

    @Test
    public void validateSeanceWithWrongDateAndTime() {
        exception.expect(SeanceCreateException.class);
        exception.expectMessage("wrong_date_and_time");

        Seance seance = Seance.builder()
                .time(LocalTime.of(9, 0))
                .date(LocalDate.MIN)
                .build();

        when(seanceRepository.findSeanceByDateAndTime(seance.getDate(), seance.getTime())).thenReturn(Optional.of(seance));

        seanceCreateValidator.validate(seance);
    }

    @Test
    public void validateSeanceWithWrongSeatingCapacity() {
        exception.expect(SeanceCreateException.class);
        exception.expectMessage("wrong_seating_capacity");

        Seance seance = Seance.builder()
                .time(LocalTime.of(9, 0))
                .date(LocalDate.MIN)
                .seatingCapacity(305)
                .build();

        when(seanceRepository.findSeanceByDateAndTime(seance.getDate(), seance.getTime())).thenReturn(Optional.empty());

        seanceCreateValidator.validate(seance);
    }

    @Test
    public void validateSeanceWithWrongFreePlaces() {
        exception.expect(SeanceCreateException.class);
        exception.expectMessage("wrong_count_of_free_places");

        Seance seance = Seance.builder()
                .time(LocalTime.of(9, 0))
                .date(LocalDate.MIN)
                .seatingCapacity(300)
                .freePlaces(320)
                .build();

        when(seanceRepository.findSeanceByDateAndTime(seance.getDate(), seance.getTime())).thenReturn(Optional.empty());

        seanceCreateValidator.validate(seance);
    }
}