package com.cinema.project.seance;

import com.cinema.project.movie.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeanceServiceTest {

    @Mock
    private SeanceRepository seanceRepository;
    @Mock
    private SeanceCreateValidator seanceCreateValidator;
    @Mock
    private SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;
    @InjectMocks
    private SeanceService seanceService;

    private final Seance expectedSeance = new Seance(1L, LocalDate.of(2, 2, 2), LocalTime.MIN, 1L, 50.0, 300, 300);
    private final Movie expectedMovie = new Movie(1L, "title");
    private final List<Seance> expectedSeancesList = Collections.singletonList(expectedSeance);
    private final List<Long> ids = Collections.singletonList(expectedSeance.getMovieId());

    @Test
    public void getAllSeances() {
        when(seanceRepository.getAllSeances()).thenReturn(expectedSeancesList);

        assertEquals(expectedSeancesList, seanceService.getAllSeances());
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

    @Test
    public void getSeancesPerPage() {
        when(seanceRepository.getSeancesPerPage(anyInt(), anyInt())).thenReturn(expectedSeancesList);

        assertEquals(expectedSeancesList, seanceService.getSeancesPerPage(10, "0"));
    }

    @Test
    public void getSeancesPerPageByIds() {
        when(seanceRepository.getSeancesPerPageByIds(anyList(), anyInt(), anyInt())).thenReturn(expectedSeancesList);

        assertEquals(expectedSeancesList, seanceService.getSeancesPerPageByIds(10, ids, "0"));
    }

}