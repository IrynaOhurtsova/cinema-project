package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import liquibase.pro.packaged.L;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceServiceTest {

    @Mock
    private SeanceRepository seanceRepository;
    @Mock
    private MovieService movieService;
    @Mock
    private SeanceCreateValidator seanceCreateValidator;
    @Mock
    private SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;

    private SeanceService seanceService;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final SeanceWithMovieTitleDto expectedSeanceDto = new SeanceWithMovieTitleDto(expectedSeance, expectedMovie);
    private final List<Seance> expectedSeances = Collections.singletonList(expectedSeance);

    @Before
    public void init() {
        Integer counterSeancesPerPage = 10;
        seanceService = new SeanceService(seanceRepository, movieService, seanceCreateValidator, seanceCreateDtoToSeanceMapper, counterSeancesPerPage);
    }


    @Test
    public void getAllSeances() {
        when(seanceRepository.getAllSeances()).thenReturn(expectedSeances);
        when(movieService.getMoviesById(Collections.singletonList(expectedSeance.getMovieId()), Locale.CANADA)).thenReturn(Collections.singletonMap(expectedMovie.getId(), expectedMovie));

        List<SeanceWithMovieTitleDto> result = seanceService.getAllSeances(Locale.CANADA);

        assertTrue(result.contains(expectedSeanceDto));
        assertEquals(Collections.singletonList(expectedSeanceDto), result);
    }

    @Test
    public void createSeance() {
        SeanceCreateDto seanceCreateDto = new SeanceCreateDto();
        seanceCreateDto.setDate(expectedSeance.getDate());
        seanceCreateDto.setTime(expectedSeance.getTime());
        seanceCreateDto.setTitle(expectedMovie.getTitle());
        seanceCreateDto.setPrice(expectedSeance.getPrice());
        seanceCreateDto.setSeatingCapacity(expectedSeance.getSeatingCapacity());
        seanceCreateDto.setFreePlaces(expectedSeance.getFreePlaces());

        Seance seanceAfterMapping = Seance.builder()
                .date(expectedSeance.getDate())
                .time(expectedSeance.getTime())
                .movieId(expectedMovie.getId())
                .price(expectedSeance.getPrice())
                .seatingCapacity(expectedSeance.getSeatingCapacity())
                .freePlaces(expectedSeance.getFreePlaces())
                .build();

        when(seanceCreateDtoToSeanceMapper.map(seanceCreateDto, Locale.CANADA)).thenReturn(seanceAfterMapping);
        when(seanceCreateValidator.validate(seanceAfterMapping)).thenReturn(seanceAfterMapping);
        when(seanceRepository.createSeance(seanceAfterMapping)).thenReturn(expectedSeance);

        Seance result = seanceService.createSeance(seanceCreateDto, Locale.CANADA);

        assertEquals(expectedSeance, result);
    }

    @Test
    public void deleteSeanceById() {
        when(seanceRepository.findSeanceByID(expectedSeance.getId())).thenReturn(Optional.of(expectedSeance));
        when(seanceRepository.deleteSeanceById(expectedSeance)).thenReturn(expectedSeance);

        Seance result = seanceService.deleteSeanceById(expectedSeance.getId());

        assertEquals(expectedSeance, result);
    }

    @Test(expected = SeanceNotFoundException.class)
    public void deleteSeanceByIdThrowsException() {
        when(seanceRepository.findSeanceByID(expectedSeance.getId())).thenReturn(Optional.empty());

        seanceService.deleteSeanceById(expectedSeance.getId());
    }

    @Test
    public void getSeancesByIds() {
        Map<Long, SeanceWithMovieTitleDto> expected = Collections.singletonMap(expectedSeanceDto.getId(), expectedSeanceDto);
        List<Long> ids = Collections.singletonList(expectedSeance.getId());

        when(seanceRepository.getSeancesByIds(ids)).thenReturn(Collections.singletonList(expectedSeance));
        when(movieService.getMoviesById(Collections.singletonList(expectedSeance.getMovieId()), Locale.CANADA)).thenReturn(Collections.singletonMap(expectedMovie.getId(), expectedMovie));

        Map<Long, SeanceWithMovieTitleDto> result = seanceService.getSeancesByIds(ids, Locale.CANADA);

        assertEquals(expected, result);
    }

    @Test
    public void testGetSeancesByIds() {
        List<Seance> expected = Collections.singletonList(expectedSeance);
        List<Long> ids = Collections.singletonList(expectedSeance.getId());

        when(seanceRepository.getSeancesByIds(ids)).thenReturn(expected);

        List<Seance> result = seanceService.getSeancesByIds(ids);

        assertEquals(expected, result);
    }

    @Test
    public void getSeanceById() {
        when(seanceRepository.findSeanceByID(expectedSeance.getId())).thenReturn(Optional.of(expectedSeance));

        Seance result = seanceService.getSeanceById(expectedSeance.getId());

        assertEquals(expectedSeance, result);
    }

    @Test(expected = SeanceNotFoundException.class)
    public void getSeanceByIdThrowsException() {
        when(seanceRepository.findSeanceByID(expectedSeance.getId())).thenReturn(Optional.empty());

        seanceService.getSeanceById(expectedSeance.getId());
    }

    @Test
    public void getSeancesPerPage() {
        when(seanceRepository.getSeancesPerPage(anyInt(), anyInt())).thenReturn(Collections.singletonList(expectedSeance));
        when(movieService.getMoviesById(Collections.singletonList(expectedSeance.getMovieId()), Locale.CANADA)).thenReturn(Collections.singletonMap(expectedMovie.getId(), expectedMovie));

        List<SeanceWithMovieTitleDto> result = seanceService.getSeancesPerPage("0", Locale.CANADA);

        assertEquals(Collections.singletonList(expectedSeanceDto), result);
    }

    @Test
    public void getPageAndFirstValue() {
        when(seanceRepository.getAllSeances()).thenReturn(Collections.singletonList(expectedSeance));

        Map<Integer, Integer> expected = Collections.singletonMap(1, 0);

        Map<Integer, Integer> result = seanceService.getPageAndFirstValue();

        assertEquals(expected, result);
    }

    @Test
    public void getSeancesPerPageByIds() {
        when(seanceRepository.getSeancesPerPageByIds(anyList(),anyInt(), anyInt())).thenReturn(Collections.singletonList(expectedSeance));
        when(movieService.getMoviesById(Collections.singletonList(expectedSeance.getMovieId()), Locale.CANADA)).thenReturn(Collections.singletonMap(expectedMovie.getId(), expectedMovie));

        List<SeanceWithMovieTitleDto> result = seanceService.getSeancesPerPageByIds(Collections.singletonList(1L),"0", Locale.CANADA);

        assertEquals(Collections.singletonList(expectedSeanceDto), result);
    }

    @Test
    public void findPageAndFirstValue() {
        Map<Integer, Integer> expected = Collections.singletonMap(1, 0);

        Map<Integer, Integer> result = seanceService.findPageAndFirstValue(Collections.singletonList(expectedSeance));

        assertEquals(expected, result);
    }
}