package com.inai.courseproject.services;

import com.inai.courseproject.dto.CardDto;
import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.exceptions.LargeTimeSpanException;
import com.inai.courseproject.exceptions.OvercrowdedClassException;
import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.Subscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionsRepository subscriptionsRepository;
    private final FitnessClassesRepository fitnessClassesRepository;
    private final UsersRepository usersRepository;
    private final ClientsSubscriptionsRepository clientsSubscriptionsRepository;
    private final ScheduleService scheduleService;


    private User saveClient(ClientsSubscriptionDto clientsSubscriptionDto) {
        User client = new User(clientsSubscriptionDto.getClientsFullName(), clientsSubscriptionDto.getEmail(), clientsSubscriptionDto.getPhone());
        return usersRepository.save(client);
    }

    public Subscription getSubscription(ClientsSubscriptionDto clientsSubscriptionDto, boolean isGroup) {
        String nameOfFitnessClass = clientsSubscriptionDto.getNameOfFitnessClass();
        return subscriptionsRepository.findSubscription(nameOfFitnessClass, clientsSubscriptionDto.getNumberOfClasses(), isGroup);
    }

    public Optional<FitnessClass> getFitnessClass(ClientsSubscriptionDto clientsSubscriptionDto, boolean isGroup) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(clientsSubscriptionDto.getTime(), dtf);

        return fitnessClassesRepository.findFitnessClass(clientsSubscriptionDto.getCoachesFullName(),
                clientsSubscriptionDto.getNameOfFitnessClass(), isGroup, time, Days.valueOf(clientsSubscriptionDto.getDays().toUpperCase()));
    }

    public void checkNumberOfPeopleInClass(FitnessClass fitnessClass) {
        if (scheduleService.getNumberOfVacantPlaces(fitnessClass) == 0)
            throw new OvercrowdedClassException("The group is already full");
    }

    public void checkStartDate(LocalDate startDate) {
        if (startDate.isAfter(LocalDate.now().plusDays(14)))
            throw new LargeTimeSpanException("The subscription must be activated within two weeks");
    }

    private void createSubscription(ClientsSubscriptionDto clientsSubscriptionDto, boolean isGroup) {
        Optional<FitnessClass> fitnessClass = getFitnessClass(clientsSubscriptionDto, isGroup);
        if (fitnessClass.isPresent()) {

            LocalDate startDate = LocalDate.parse(clientsSubscriptionDto.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            checkStartDate(startDate);

            checkNumberOfPeopleInClass(fitnessClass.get());
            User client = saveClient(clientsSubscriptionDto);
            Subscription subscription = getSubscription(clientsSubscriptionDto, isGroup);

            clientsSubscriptionsRepository.save(new ClientsSubscription(client, subscription, LocalDate.now(),
                    startDate, fitnessClass.get(), true));
        }
    }

    public void subscribe(ClientsSubscriptionDto clientsSubscriptionDto) {
        createSubscription(clientsSubscriptionDto, clientsSubscriptionDto.getTypeOfFitnessClass().equalsIgnoreCase("group"));
    }

    public boolean isCardValid(CardDto cardDto) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth expiredDate = YearMonth.parse(cardDto.getExpiredDate(), dtf);

        return true;
    }
}
