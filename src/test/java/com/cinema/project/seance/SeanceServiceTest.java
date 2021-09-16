package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import com.cinema.project.movie.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
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
   // private final SeanceWithMovieTitleDto expectedSeanceDto = new SeanceWithMovieTitleDto(expectedSeance, expectedMovie);
    private final List<Seance> expectedSeancesList = Collections.singletonList(expectedSeance);
    private final List<Long> ids = Collections.singletonList(expectedSeance.getMovieId());
    private final Map<Long, Movie> moviesById = Collections.singletonMap(expectedMovie.getId(), expectedMovie);
    //private final List<SeanceWithMovieTitleDto> expectedSeancesDtoList = Collections.singletonList(expectedSeanceDto);

    @Before
    public void init() {
        Integer counterSeancesPerPage = 10;
        seanceService = new SeanceService(seanceRepository,seanceCreateValidator, seanceCreateDtoToSeanceMapper, counterSeancesPerPage);
    }


//    @Test
//    public void getAllSeances() {
//        when(seanceRepository.getAllSeances()).thenReturn(expectedSeancesList);
//        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);
//
//        List<SeanceWithMovieTitleDto> result = seanceService.getAllSeancesDto(Locale.CANADA);
//
//        assertTrue(result.contains(expectedSeanceDto));
//        assertEquals(expectedSeancesDtoList, result);
//    }

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

//    @Test
//    public void getSeancesByIds() {
//        Map<Long, SeanceWithMovieTitleDto> expected = Collections.singletonMap(expectedSeanceDto.getId(), expectedSeanceDto);
//
//        when(seanceRepository.getSeancesByIds(ids)).thenReturn(expectedSeancesList);
//        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);
//
//        Map<Long, SeanceWithMovieTitleDto> result = seanceService.getSeancesByIds(ids, Locale.CANADA);
//
//        assertEquals(expected, result);
//    }

    @Test
    public void testGetSeancesByIds() {
        when(seanceRepository.getSeancesByIds(ids)).thenReturn(expectedSeancesList);

        List<Seance> result = seanceService.getSeancesByIds(ids);

        assertEquals(expectedSeancesList, result);
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

//    @Test
//    public void getSeancesPerPage() {
//        when(seanceRepository.getSeancesPerPage(anyInt(), anyInt())).thenReturn(expectedSeancesList);
//        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);
//
//        List<SeanceWithMovieTitleDto> result = seanceService.getSeancesPerPageDto("0", Locale.CANADA);
//
//        assertEquals(expectedSeancesDtoList, result);
//    }

//    @Test
//    public void getPageAndFirstValue() {
//        when(seanceRepository.getAllSeances()).thenReturn(expectedSeancesList);
//
//        Map<Integer, Integer> expected = Collections.singletonMap(1, 0);
//
//        Map<Integer, Integer> result = seanceService.getPageAndFirstValue();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void getSeancesPerPageByIds() {
//        when(seanceRepository.getSeancesPerPageByIds(ids, 10, 0)).thenReturn(expectedSeancesList);
//        when(movieService.getMoviesById(ids, Locale.CANADA)).thenReturn(moviesById);
//
//        List<SeanceWithMovieTitleDto> result = seanceService.getSeancesPerPageByIdsDto(ids, "0", Locale.CANADA);
//
//        assertEquals(expectedSeancesDtoList, result);
//    }

    @Test
    public void findPageAndFirstValue() {
        Map<Integer, Integer> expected = Collections.singletonMap(1, 0);

        Map<Integer, Integer> result = seanceService.findPageAndFirstValue(expectedSeancesList);

        assertEquals(expected, result);
    }
}