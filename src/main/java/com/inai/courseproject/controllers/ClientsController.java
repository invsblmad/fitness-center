package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.*;
import com.inai.courseproject.services.ClientsService;
import com.inai.courseproject.services.SubscriptionService;
import com.inai.courseproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {

    private final ClientsService clientsService;
    private final UsersService usersService;
    private final SubscriptionService subscriptionService;

    @GetMapping()
    public String greet() {
        return usersService.greet();
    }

    @GetMapping("/my-account")
    public UserAccountDto getClientsInfo() {
        return usersService.getUsersInfo();
    }

    @PutMapping("/my-account")
    public void updateClientsInfo(@RequestBody UserAccountDto userAccountDto) {
        usersService.updateUsersInfo(userAccountDto);
    }

    @GetMapping("/my-progress")
    public PersonalParametersDto getClientsParameters(@RequestParam(value = "month")Optional<String> month,
                                                      @RequestParam(value = "year") Optional<String> year) {
        return clientsService.getClientsParameters(month, year);
    }

    @PostMapping("/my-progress")
    public void createClientsParameters(@RequestBody PersonalParametersDto personalParametersDto) {
        clientsService.createClientsParameters(personalParametersDto);
    }

    @GetMapping("/my-subscriptions")
    public List<ActiveSubscriptionDto> getAllClientsActiveSubscriptions() {
        return clientsService.getAllClientsActiveSubscriptions();
    }

    @GetMapping("/my-schedule/today")
    public List<ClientsFitnessClassDto> getFitnessClassesForToday(@RequestParam(value = "name") Optional<String> name,
                                                           @RequestParam(value = "type") Optional<String> type,
                                                           @RequestParam(value = "time") Optional<String> time) {
        return clientsService.getClientsFitnessClassesForToday(name, type, time);
    }

    @GetMapping("/my-schedule/{day}")
    public List<ClientsFitnessClassDto> getFitnessClasses(@PathVariable String day, @RequestParam(value = "name")
            Optional<String> name, @RequestParam(value = "type") Optional<String> type, @RequestParam(value = "time")
            Optional<String> time) {
        return clientsService.getClientsFitnessClasses(day, name, type, time);
    }

    @PostMapping("buy-subscription/check-card")
    public boolean subscribe(@RequestBody CardDto cardDto) {
        return subscriptionService.isCardValid(cardDto);
    }

    @PostMapping("buy-subscription/check-card/subscribe")
    public void subscribe(@RequestBody ClientsSubscriptionDto clientsSubscriptionDto) {
        clientsService.subscribe(clientsSubscriptionDto);
    }


}
