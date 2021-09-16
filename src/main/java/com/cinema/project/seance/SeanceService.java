package com.cinema.project.seance;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final SeanceCreateValidator seanceCreateValidator;
    private final SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper;
    private final Integer counterSeancesPerPage;

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

    public Map<Integer, Integer> findPageAndFirstValue(List<Seance> seances) {
        int countOfPage = (int) Math.ceil((double) seances.size() / counterSeancesPerPage);
        return IntStream.range(0, countOfPage)
                .boxed()
                .collect(Collectors.toMap(page -> page + 1, firstValue -> firstValue * counterSeancesPerPage));
    }

    public List<Seance> getSeancesPerPage(String firstValue) {
        return seanceRepository.getSeancesPerPage(counterSeancesPerPage, Integer.valueOf(firstValue));
    }

    public List<Seance> getSeancesPerPageByIds(List<Long> ids, String firstValue) {
        return seanceRepository.getSeancesPerPageByIds(ids, counterSeancesPerPage, Integer.valueOf(firstValue));
    }

}
