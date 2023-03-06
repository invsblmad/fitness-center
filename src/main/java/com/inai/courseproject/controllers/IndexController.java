package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.*;
import com.inai.courseproject.services.IndexService;
import com.inai.courseproject.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;
    private final ScheduleService scheduleService;

    @GetMapping("/about-us")
    public List<CoachDto> getCoachesInfo() {
        return indexService.getCoachesInfo();
    }

    @GetMapping("/our-services")
    public List<TypeOfClassDto> getTypesOfFitnessClasses() {
        return indexService.getTypesOfFitnessClasses();
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionDto> getSubscriptions() {
        return indexService.getSubscriptions();
    }

    @GetMapping("/schedule/days")
    public List<DayOfWeekDto> getDaysOfWeek() {
        return indexService.getDaysOfWeek();
    }

    @GetMapping("/schedule/today")
    public List<FitnessClassDto> getFitnessClassesForToday(@RequestParam(value = "name") Optional<String> name,
                                                           @RequestParam(value = "type") Optional<String> type,
                                                           @RequestParam(value = "time") Optional<String> time) {
        return scheduleService.getFitnessClassesForToday(name, type, time);
    }

    @GetMapping("/schedule/{day}")
    public List<FitnessClassDto> getFitnessClasses(@PathVariable String day, @RequestParam(value = "name") Optional<String> name,
                                                   @RequestParam(value = "type") Optional<String> type,
                                                   @RequestParam(value = "time") Optional<String> time) {
        return scheduleService.getFitnessClasses(day, name, type, time);
    }

}
