package com.inai.courseproject.services;

import com.inai.courseproject.dto.ActiveSubscriptionDto;
import com.inai.courseproject.dto.ClientsFitnessClassDto;
import com.inai.courseproject.dto.PersonalParametersDto;
import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.Subscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.ClientsParameters;
import com.inai.courseproject.repositories.ClientsParametersRepository;
import com.inai.courseproject.repositories.ClientsSubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientsService {

    private final ClientsSubscriptionsRepository clientsSubscriptionsRepository;
    private final ClientsParametersRepository clientsParametersRepository;
    private final SubscriptionService subscriptionService;
    private final AuthService authService;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");


    private String getNumberInDateFormat(int number) {
        if (number <= 9) return "0" + number;
        else return String.valueOf(number);
    }

    private Integer getDayOfSettingParameters() {
        Optional<ClientsParameters> clientsParameters = clientsParametersRepository.findFirstByClient(authService.getUser());
        return clientsParameters.map(parameters -> parameters.getSetDate().getDayOfMonth()).orElse(0);
    }

    private LocalDate getDate(int numberOfMonth, String year) {
        if (getDayOfSettingParameters() != 0) {
            String day = getNumberInDateFormat(getDayOfSettingParameters());
            String month =  getNumberInDateFormat(numberOfMonth);

            String date = "%s/%s/%s".formatted(day, month, year);
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public PersonalParametersDto getClientsParameters(Optional<String> month, Optional<String> year) {
        if (month.isPresent() && year.isPresent())
            return getClientsParametersByDate(month.get(), year.get());
        else
            return getLatestClientsParameters();
    }

    private PersonalParametersDto getLatestClientsParameters() {
        if (getDayOfSettingParameters() != 0) {
            ClientsParameters parameters = clientsParametersRepository.findAllByClientOrderBySetDateDesc(authService.getUser()).get(0);
            return PersonalParametersDto.parseClientsParametersToDto(parameters);
        } else return null;
    }

    private PersonalParametersDto getClientsParametersByDate(String month, String year) {
        int numberOfMonth = Month.valueOf(month.toUpperCase()).getValue();
        LocalDate date = getDate(numberOfMonth, year);
        if (date != null) {
            Optional<ClientsParameters> parameters = clientsParametersRepository.findBySetDateAndClient(date, authService.getUser());
            return parameters.map(PersonalParametersDto::parseClientsParametersToDto).orElse(null);
        } else return null;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void createClientsParameters(PersonalParametersDto personalParametersDto) {
        ClientsParameters parameters = PersonalParametersDto.parseDtoToClientsParameters(
                personalParametersDto, LocalDate.now(), authService.getUser());
        clientsParametersRepository.save(parameters);
    }

    private List<ActiveSubscriptionDto> getClientsSubscriptionsDto(List<ClientsSubscription> subscriptions) {
        return subscriptions.stream().map(s ->
                new ActiveSubscriptionDto(
                        s.getSubscription().getTypeOfFitnessClass().getName(),
                        s.getSubscription().getNumberOfClasses(),
                        s.getFitnessClass().getIsGroupOrNot(),
                        s.getFitnessClass().getCoach().getFullName(),
                        s.getFitnessClass().getTime().toString(),
                        s.getFitnessClass().getDaysAsString(),
                        s.getStartDate().toString()
                )).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<ActiveSubscriptionDto> getAllClientsActiveSubscriptions() {
        List<ClientsSubscription> subscriptions = clientsSubscriptionsRepository.findAllByClient(authService.getUser(), true);
        return getClientsSubscriptionsDto(subscriptions);
    }

    private List<FitnessClass> getFitnessClasses(List<ClientsSubscription> clientsSubscriptions) {
        return clientsSubscriptions.stream().map(ClientsSubscription::getFitnessClass).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<ClientsFitnessClassDto> getClientsFitnessClassesForToday(Optional<String> name, Optional<String> type, Optional<String> time) {
        if (LocalDate.now().getDayOfWeek().getValue() % 2 == 0)
            return getClientsFitnessClasses("tu", name, type, time);
        else
            return getClientsFitnessClasses("mn", name, type, time);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<ClientsFitnessClassDto> getClientsFitnessClasses(String day, Optional<String> name,
                                                   Optional<String> type, Optional<String> time) {
        if (name.isPresent())
            return getClientsFitnessClassesByDayAndName(day, name.get());
        else if (type.isPresent())
            return getClientsFitnessClassesByDayAndType(day, type.get());
        else if (time.isPresent())
            return getClientsFitnessClassesByDayAndTime(day, time.get());
        else
            return getClientsFitnessClassesByDay(day);
    }

    private List<ClientsFitnessClassDto> getClientsFitnessClassesByDay(String day) {
        List<ClientsSubscription> clientsSubscriptions;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysOfClass(authService.getUser(), true, Days.ODD);
        else
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysOfClass(authService.getUser(), true, Days.EVEN);

        return ClientsFitnessClassDto.parseFitnessClassesToDtos(getFitnessClasses(clientsSubscriptions));
    }

    private List<ClientsFitnessClassDto> getClientsFitnessClassesByDayAndName(String day, String name) {
        List<ClientsSubscription> clientsSubscriptions;

        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysAndNameOfClass(authService.getUser(), true, Days.ODD, name);
        else
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysAndNameOfClass(authService.getUser(), true, Days.EVEN, name);

        return ClientsFitnessClassDto.parseFitnessClassesToDtos(getFitnessClasses(clientsSubscriptions));
    }

    private List<ClientsSubscription> getByType(Days days, String type) {
        if (type.equals("group"))
            return clientsSubscriptionsRepository.findAllByClientAndDaysAndTypeOfClass(authService.getUser(), true, days, true);
        else
            return clientsSubscriptionsRepository.findAllByClientAndDaysAndTypeOfClass(authService.getUser(), true, days, false);
    }

    private List<ClientsFitnessClassDto> getClientsFitnessClassesByDayAndType(String day, String type) {
        List<ClientsSubscription> clientsSubscriptions;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            clientsSubscriptions = getByType(Days.ODD, type);
        else
            clientsSubscriptions = getByType(Days.EVEN, type);

        return ClientsFitnessClassDto.parseFitnessClassesToDtos(getFitnessClasses(clientsSubscriptions));
    }

    private List<ClientsFitnessClassDto> getClientsFitnessClassesByDayAndTime(String day, String time) {
        List<ClientsSubscription> clientsSubscriptions;

        LocalTime parsedTime = LocalTime.parse(time, dtf);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysAndTimeOfClass(authService.getUser(), true, Days.ODD, parsedTime);
        else
            clientsSubscriptions = clientsSubscriptionsRepository.findAllByClientAndDaysAndTimeOfClass(authService.getUser(), true, Days.EVEN, parsedTime);

        return ClientsFitnessClassDto.parseFitnessClassesToDtos(getFitnessClasses(clientsSubscriptions));
    }

    public void createSubscription(ClientsSubscriptionDto clientsSubscriptionDto, boolean isGroup) {
        Optional<FitnessClass> fitnessClass = subscriptionService.getFitnessClass(clientsSubscriptionDto, isGroup);
        if (fitnessClass.isPresent()) {

            subscriptionService.checkNumberOfPeopleInClass(fitnessClass.get());
            Subscription subscription = subscriptionService.getSubscription(clientsSubscriptionDto, isGroup);

            LocalDate startDate = LocalDate.parse(clientsSubscriptionDto.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            subscriptionService.checkStartDate(startDate);

            clientsSubscriptionsRepository.save(new ClientsSubscription(authService.getUser(), subscription, LocalDate.now(),
                    startDate, fitnessClass.get(), true));
        }
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void subscribe(ClientsSubscriptionDto clientsSubscriptionDto) {
        createSubscription(clientsSubscriptionDto, clientsSubscriptionDto.getTypeOfFitnessClass().equalsIgnoreCase("group"));
    }



}
