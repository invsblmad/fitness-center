package com.inai.courseproject.services;

import com.inai.courseproject.dto.ClientsParametersDto;
import com.inai.courseproject.dto.CoachesFitnessClassDto;
import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.ClientsParameters;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.ClientsParametersRepository;
import com.inai.courseproject.repositories.ClientsSubscriptionsRepository;
import com.inai.courseproject.repositories.FitnessClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachesService {

    private final FitnessClassesRepository fitnessClassesRepository;
    private final AuthService authService;
    private final ClientsSubscriptionsRepository clientsSubscriptionsRepository;
    private final ClientsParametersRepository clientsParametersRepository;

    public int getNumberOfPeopleInClass(FitnessClass fitnessClass) {
        return (int) clientsSubscriptionsRepository.findByFitnessClass(fitnessClass)
                .stream().filter(ClientsSubscription::isActive).count();
    }

    public List<CoachesFitnessClassDto> parseFitnessClassesToDtos(List<FitnessClass> fitnessClasses) {
        return fitnessClasses.stream().map(f -> CoachesFitnessClassDto.parseFitnessClassToDto(f, getNumberOfPeopleInClass(f)))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_COACH')")
    public List<CoachesFitnessClassDto> getFitnessClassesForToday(Optional<String> name, Optional<String> type, Optional<String> time) {
        if (LocalDate.now().getDayOfWeek().getValue() % 2 == 0)
            return getFitnessClasses("tu", name, type, time);
        else
            return getFitnessClasses("mn", name, type, time);
    }

    @PreAuthorize("hasRole('ROLE_COACH')")
    public List<CoachesFitnessClassDto> getFitnessClasses(String day, Optional<String> name,
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

    private List<CoachesFitnessClassDto> getFitnessClassesByDay(String day) {
        List<FitnessClass> fitnessClasses;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDays(authService.getUser(), Days.ODD);
        else
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDays(authService.getUser(), Days.EVEN);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<CoachesFitnessClassDto> getFitnessClassesByDayAndName(String day, String name) {
        List<FitnessClass> fitnessClasses;

        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndName(authService.getUser(), Days.ODD, name);
        else
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndName(authService.getUser(), Days.EVEN, name);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<CoachesFitnessClassDto> getFitnessClassesByDayAndType(String day, String type) {
        List<FitnessClass> fitnessClasses;

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndType(authService.getUser(), Days.ODD, type.equals("group"));
        else
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndType(authService.getUser(), Days.EVEN, type.equals("group"));

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private List<CoachesFitnessClassDto> getFitnessClassesByDayAndTime(String day, String time) {
        List<FitnessClass> fitnessClasses;

        LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

        if (day.equals("mn") || day.equals("wd") || day.equals("fr"))
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndTime(authService.getUser(), Days.ODD, parsedTime);
        else
            fitnessClasses = fitnessClassesRepository.findAllByCoachAndDaysAndTime(authService.getUser(), Days.EVEN, parsedTime);

        return parseFitnessClassesToDtos(fitnessClasses);
    }

    private String getNumberInDateFormat(int number) {
        if (number <= 9) return "0" + number;
        else return String.valueOf(number);
    }

    private Integer getDayOfSettingParameters(User client) {
        Optional<ClientsParameters> clientsParameters = clientsParametersRepository.findFirstByClient(client);
        return clientsParameters.map(parameters -> parameters.getSetDate().getDayOfMonth()).orElse(0);
    }

    private LocalDate getDate(int numberOfMonth, String year, User client) {
        if (getDayOfSettingParameters(client) != 0) {
            String day = getNumberInDateFormat(getDayOfSettingParameters(client));
            String month =  getNumberInDateFormat(numberOfMonth);

            String date = "%s/%s/%s".formatted(day, month, year);
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_COACH')")
    public List<ClientsParametersDto> getClientsParameters(Optional<String> month, Optional<String> year) {
        if (month.isPresent() && year.isPresent())
            return getClientsParametersByDate(month.get(), year.get());
        else
            return getLatestClientsParameters();
    }

    private List<ClientsParametersDto> getLatestClientsParameters() {
        List<User> clients = clientsSubscriptionsRepository.findAllByCoach(authService.getUser()).
                stream().map(ClientsSubscription::getClient).collect(Collectors.toList());

        List<ClientsParametersDto> allParameters = new ArrayList<>();

        for (User client: clients) {
            if (getDayOfSettingParameters(client) != 0) {
                ClientsParameters parameters = clientsParametersRepository.findAllByClientOrderBySetDateDesc(client).get(0);
                allParameters.add(ClientsParametersDto.parseClientsParametersToDto(parameters));
            }
        }
        return allParameters;
    }

    private List<ClientsParametersDto> getClientsParametersByDate(String month, String year) {
        List<User> clients = clientsSubscriptionsRepository.findAllByCoach(authService.getUser()).
                stream().map(ClientsSubscription::getClient).collect(Collectors.toList());

        List<ClientsParametersDto> allParameters = new ArrayList<>();
        int numberOfMonth = Month.valueOf(month.toUpperCase()).getValue();

        for (User client: clients) {
            LocalDate date = getDate(numberOfMonth, year, client);
            if (date != null) {
                Optional<ClientsParameters> parameters = clientsParametersRepository.findBySetDateAndClient(date, client);
                parameters.ifPresent(clientsParameters -> allParameters.add(
                        ClientsParametersDto.parseClientsParametersToDto(clientsParameters)));
            }
        }
        return allParameters;
    }
}
