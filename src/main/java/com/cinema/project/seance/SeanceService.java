package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final SeanceCreateValidator seanceCreateValidator;
    private final SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;

    public List<Seance> getAllSeances() {
        return seanceRepository.getAllSeances();
    }

    public Seance createSeance(SeanceCreateDto seanceCreateDto, Locale locale) {
        Seance seance = seanceCreateDtoToSeanceMapper.map(seanceCreateDto, locale);
        return seanceRepository.createSeance(seanceCreateValidator.validate(seance));
    }

    public Seance deleteSeanceById(Long id) {
        return seanceRepository.deleteSeanceById(getSeanceById(id));
    }

    public List<Seance> getSeancesByIds(List<Long> ids) {
        return seanceRepository.getSeancesByIds(ids);
    }

    public Seance getSeanceById(Long id) {
        return seanceRepository.findSeanceByID(id)
                .orElseThrow(() -> new SeanceNotFoundException("seance doesn't exist"));
    }

    public List<Seance> getSeancesPerPage(Integer counterSeancesPerPage, String firstValue) {
        return seanceRepository.getSeancesPerPage(counterSeancesPerPage, Integer.valueOf(firstValue));
    }

    public List<Seance> getSeancesPerPageByIds(Integer counterSeancesPerPage, List<Long> ids, String firstValue) {
        return seanceRepository.getSeancesPerPageByIds(ids, counterSeancesPerPage, Integer.valueOf(firstValue));
    }

}
