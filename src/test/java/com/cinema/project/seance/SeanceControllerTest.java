package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceControllerTest {

    @Mock
    private SeanceService seanceService;
    @InjectMocks
    private SeanceController seanceController;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");

    @Test
    public void createSeance() {
        String expected = "redirect:/cinema/mainpage";

        SeanceCreateDto seanceCreateDto = new SeanceCreateDto();
        seanceCreateDto.setDate(expectedSeance.getDate());
        seanceCreateDto.setTime(expectedSeance.getTime());
        seanceCreateDto.setTitle(expectedMovie.getTitle());
        seanceCreateDto.setPrice(expectedSeance.getPrice());
        seanceCreateDto.setSeatingCapacity(expectedSeance.getSeatingCapacity());
        seanceCreateDto.setFreePlaces(expectedSeance.getFreePlaces());

        when(seanceService.createSeance(seanceCreateDto, Locale.CANADA)).thenReturn(expectedSeance);

        assertEquals(expected, seanceController.createSeance(seanceCreateDto, Locale.CANADA));
    }

    @Test
    public void delete() {
        String expected = "redirect:/cinema/mainpage";

        when(seanceService.deleteSeanceById(expectedSeance.getId())).thenReturn(expectedSeance);

        assertEquals(expected, seanceController.delete(String.valueOf(expectedSeance.getId())));
    }

}