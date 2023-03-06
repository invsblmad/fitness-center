package com.inai.courseproject.services;

import com.inai.courseproject.dto.FitnessClassDto;
import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.repositories.ClientsSubscriptionsRepository;
import com.inai.courseproject.repositories.FitnessClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final FitnessClassesRepository fitnessClassesRepository;
    private final ClientsSubscriptionsRepository clientsSubscriptionsRepository;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");


    public int getNumberOfVacantPlaces(FitnessClass fitnessClass) {
        int numberOfPeopleInClass = (int) clientsSubscriptionsRepository.findByFitnessClass(fitnessClass)
                .stream().filter(ClientsSubscription::isActive).count();
        if (fitnessClass.isGroup()) return 10 - numberOfPeopleInClass;
        else return 1 - numberOfPeopleInClass;
    }

    public List<FitnessClassDto> parseFitnessClassesToDtos(List<FitnessClass> fitnessClasses) {
        return fitnessClasses.stream().map(f -> FitnessClassDto.parseFitnessClassToDto(f, getNumberOfVacantPlaces(f)))
                .collect(Collectors.toList());
    }

    public List<FitnessClassDto> getFitnessClassesForToday(Optional<String> name, Optional<String> type, Optional<String> time) {
        if (LocalDate.now().getDayOfWeek().getValue() % 2 == 0)
            return getFitnessClasses("tu", name, type, time);
        else
            return getFitnessClasses("mn", name, type, time);
    }

    public List<FitnessClassDto> getFitnessClasses(String day, Optional<String> name,
                                                   Optional<String> type, Optional<String> time) {
        if (name.isPresent())
            return getFitnessClassesByDayAndName(day, name.get());
        else if (type.isPresent())
            return getFitnessClassesByDayAndType(day, type.get());
        else if (time.isPresent())
            return getFitnessClassesByDayAndTime(day, time.get());
        else
            return getFitnessClassesByDay(day);
    }

    private List<FitnessClassDto> getFitnessClassesByDay(String day) {
        List<FitnessClass> fitnessClasses;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByDays(Days.ODD);
        else
            fitnessClasses = fitnessClassesRepository.findAllByDays(Days.EVEN);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<FitnessClassDto> getFitnessClassesByDayAndName(String day, String name) {
        List<FitnessClass> fitnessClasses;

        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByDaysAndName(Days.ODD, name);
        else
            fitnessClasses = fitnessClassesRepository.findAllByDaysAndName(Days.EVEN, name);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<FitnessClass> getByType(Days days, String type) {
        if (type.equals("group"))
            return fitnessClassesRepository.findAllByDaysAndType(days, true);
        else
            return fitnessClassesRepository.findAllByDaysAndType(days, false);
    }

    private List<FitnessClassDto> getFitnessClassesByDayAndType(String day, String type) {
        List<FitnessClass> fitnessClasses;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = getByType(Days.ODD, type);
        else
            fitnessClasses = getByType(Days.EVEN, type);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<FitnessClassDto> getFitnessClassesByDayAndTime(String day, String time) {
        List<FitnessClass> fitnessClasses;

        LocalTime parsedTime = LocalTime.parse(time, dtf);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByDaysAndTime(Days.ODD, parsedTime);
        else
            fitnessClasses = fitnessClassesRepository.findAllByDaysAndTime(Days.EVEN, parsedTime);

        return parseFitnessClassesToDtos(fitnessClasses);
    }
}
