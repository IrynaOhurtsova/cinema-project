package com.cinema.project.schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule fullSchedule() {
        return scheduleRepository.getFullSchedule();
    }
}
