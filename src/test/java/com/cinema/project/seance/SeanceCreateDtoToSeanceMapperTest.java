package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceCreateDtoToSeanceMapperTest {

    @Mock
    private MovieService movieService;
    @InjectMocks
    private SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;

    @Test
    public void mapDtoToSeance() {
        String title = "title";
        Double price = 50.0;
        Integer seatingCapacity = 300;
        Integer freePlaces = 300;

        SeanceCreateDto seanceCreateDto = new SeanceCreateDto();
        seanceCreateDto.setDate(LocalDate.MIN);
        seanceCreateDto.setTime(LocalTime.MIN);
        seanceCreateDto.setTitle(title);
        seanceCreateDto.setPrice(price);
        seanceCreateDto.setSeatingCapacity(seatingCapacity);
        seanceCreateDto.setFreePlaces(freePlaces);

        Movie expectedMovie = new Movie(1L, title);
        Seance expected = Seance.builder()
                .date(LocalDate.MIN)
                .time(LocalTime.MIN)
                .movieId(expectedMovie.getId())
                .price(price)
                .seatingCapacity(seatingCapacity)
                .freePlaces(freePlaces)
                .build();

        when(movieService.getMovieByTitle(seanceCreateDto.getTitle(), Locale.CANADA)).thenReturn(expectedMovie);

        Seance result = seanceCreateDtoToSeanceMapper.map(seanceCreateDto, Locale.CANADA);

        assertEquals(expected, result);

    }

    @Test
    public void mapDtoToSeance2() {
        String title = "title";
        SeanceCreateDto seanceCreateDto = new SeanceCreateDto();

        Movie expectedMovie = new Movie(1L, title);
        Seance expected = Seance.builder()
                .movieId(expectedMovie.getId())
                .build();

        when(movieService.getMovieByTitle(seanceCreateDto.getTitle(), Locale.CANADA)).thenReturn(expectedMovie);

        Seance result = seanceCreateDtoToSeanceMapper.map(seanceCreateDto, Locale.CANADA);

        assertEquals(expected, result);

    }
}