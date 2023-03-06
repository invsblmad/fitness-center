package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.CardDto;
import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("index/buy-subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/check-card")
    public boolean subscribe(@RequestBody CardDto cardDto) {
        return subscriptionService.isCardValid(cardDto);
    }

    @PostMapping("/check-card/subscribe")
    public void subscribe(@RequestBody ClientsSubscriptionDto clientsSubscriptionDto) {
        subscriptionService.subscribe(clientsSubscriptionDto);
    }

}
