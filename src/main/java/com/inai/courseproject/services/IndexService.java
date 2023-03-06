package com.inai.courseproject.services;

import com.inai.courseproject.dto.*;
import com.inai.courseproject.models.Subscription;
import com.inai.courseproject.models.fitnessClass.TypeOfFitnessClass;
import com.inai.courseproject.models.user.CoachesPortfolio;
import com.inai.courseproject.repositories.CoachesRepository;
import com.inai.courseproject.repositories.SubscriptionsRepository;
import com.inai.courseproject.repositories.TypesOfClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final CoachesRepository coachesRepository;
    private final TypesOfClassesRepository typesOfClassesRepository;
    private final SubscriptionsRepository subscriptionsRepository;


    public List<CoachDto> getCoachesInfo() {
        List<CoachesPortfolio> foundCoaches = coachesRepository.findAll();
        return foundCoaches.stream().map(c ->
                new CoachDto(
                        c.getCoach().getFullName(), c.getTypeOfFitnessClass()
                        .getName(), c.getWorkExperience(), c.getInformation()
                )).collect(Collectors.toList());
    }

    private String getPrice(String nameOfFitnessClass, int numberOfClasses, boolean isGroup) {
        String price = subscriptionsRepository.findByNumberOfClassesAndGroup(nameOfFitnessClass, numberOfClasses, isGroup).getPrice().toString();
        return price.substring(0, price.length() - 3);
    }

    private String getPriceRange(String nameOfFitnessClass) {
        if (nameOfFitnessClass.equals("Fitness") || nameOfFitnessClass.equals("Boxing"))
            return getPrice(nameOfFitnessClass,12, true) + "-" + getPrice(nameOfFitnessClass,36, false);
        else if (nameOfFitnessClass.equals("Stretching") || nameOfFitnessClass.equals("Pilates"))
            return getPrice(nameOfFitnessClass,12, true) + "-" + getPrice(nameOfFitnessClass,36, true);
        else
            return getPrice(nameOfFitnessClass,12, false) + "-" + getPrice(nameOfFitnessClass,36, false);
    }

    public List<TypeOfClassDto> getTypesOfFitnessClasses() {
        List<TypeOfFitnessClass> types = typesOfClassesRepository.findAll();
        return types.stream().map(t ->
                new TypeOfClassDto(
                        t.getName(), t.getDescription(), t.getDuration(), getPriceRange(t.getName())
                )).collect(Collectors.toList());
    }

    public List<SubscriptionDto> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionsRepository.findAll();
        return subscriptions.stream().map(s ->
                new SubscriptionDto(
                        s.getTypeOfFitnessClass().getName(),
                        s.getNumberOfClasses(),
                        s.getIsGroupOrNot(), s.getPrice().toString()
                )).collect(Collectors.toList());
    }

    public List<DayOfWeekDto> getDaysOfWeek() {
        LocalDate today = LocalDate.now();
        int weekNumber = today.get(WeekFields.of(Locale.ITALY).weekOfYear());
        return Arrays.stream(DayOfWeek.values()).map(d -> new DayOfWeekDto(
                                d.toString(), today
                                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                                .with(TemporalAdjusters.previousOrSame(d)).getDayOfMonth(),
                                d == today.getDayOfWeek()
                        )).collect(Collectors.toList());
    }

}
