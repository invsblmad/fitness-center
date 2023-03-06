package com.inai.courseproject.services;

import com.inai.courseproject.dto.FitnessClassDto;
import com.inai.courseproject.dto.SubscriptionDto;
import com.inai.courseproject.exceptions.ExistingFitnessClassException;
import com.inai.courseproject.models.Subscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.fitnessClass.LocationOfFitnessClass;
import com.inai.courseproject.models.fitnessClass.TypeOfFitnessClass;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagersService {

    private final FitnessClassesRepository fitnessClassesRepository;
    private final TypesOfClassesRepository typesOfClassesRepository;
    private final LocationsOfClassesRepository locationsOfClassesRepository;
    private final UsersRepository usersRepository;
    private final SubscriptionsRepository subscriptionsRepository;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");


    private void checkFitnessClassForUniqueness(FitnessClass fitnessClass) {
        if (fitnessClassesRepository.findFitnessClass(fitnessClass.getCoach().getFullName(), fitnessClass.getType().getName(),
                fitnessClass.isGroup(),fitnessClass.getTime(), fitnessClass.getDays()).isPresent())
            throw new ExistingFitnessClassException("Such fitness class already exists");
    }

    private FitnessClass getFitnessClass(String day, FitnessClassDto fitnessClassDto) {
        LocalTime time = LocalTime.parse(fitnessClassDto.getTime(), dtf);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            return fitnessClassesRepository.findFitnessClass(fitnessClassDto.getNameOfCoach(), fitnessClassDto.getName(),
                    FitnessClassDto.isGroup(fitnessClassDto.getType()), time, Days.ODD).orElse(null);
        else return fitnessClassesRepository.findFitnessClass(fitnessClassDto.getNameOfCoach(), fitnessClassDto.getName(),
                FitnessClassDto.isGroup(fitnessClassDto.getType()), time, Days.EVEN).orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void updateFitnessClass(String day, List<FitnessClassDto> fitnessClassDtos) {
        FitnessClassDto newFitnessClassDto = fitnessClassDtos.get(1), fitnessClassDto = fitnessClassDtos.get(0);

        FitnessClass fitnessClass = getFitnessClass(day, fitnessClassDto);

        User coach = usersRepository.findByFullName(newFitnessClassDto.getNameOfCoach());
        TypeOfFitnessClass type = typesOfClassesRepository.findByName(newFitnessClassDto.getName());
        LocationOfFitnessClass location = locationsOfClassesRepository.findByName(newFitnessClassDto.getLocation());

        fitnessClass.update(coach, type, location, LocalTime.parse(newFitnessClassDto.getTime(), dtf),
                FitnessClassDto.isGroup(newFitnessClassDto.getType()));
        checkFitnessClassForUniqueness(fitnessClass);

        fitnessClassesRepository.save(fitnessClass);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void updatePriceOfSubscriptions(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionsRepository.findSubscription(subscriptionDto.getNameOfFitnessClass(),
                subscriptionDto.getNumberOfClasses(), subscriptionDto.isGroup());

        subscription.setPrice(new BigDecimal(subscriptionDto.getPrice()));
        subscriptionsRepository.save(subscription);
    }


}
