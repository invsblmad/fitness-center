package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.*;
import com.inai.courseproject.services.AdminService;
import com.inai.courseproject.services.ManagersService;
import com.inai.courseproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagersController {

    private final ManagersService managersService;
    private final UsersService usersService;
    private final AdminService adminService;

    @GetMapping()
    public String greet() {
        return usersService.greet();
    }

    @GetMapping("/my-account")
    public StaffAccountDto getManagersInfo() {
        return usersService.getStaffInfo();
    }

    @PutMapping("/my-account")
    public void updateManagersInfo(@RequestBody StaffAccountDto staffAccountDto) {
        usersService.updateStaffInfo(
                staffAccountDto);
    }

    @PutMapping("/schedule/{day}")
    public void updateFitnessClass(@PathVariable String day, @RequestBody List<FitnessClassDto> fitnessClassDtos) {
        managersService.updateFitnessClass(day, fitnessClassDtos);
    }

    @PutMapping("/subscriptions")
    public void updatePriceOfSubscriptions(@RequestBody SubscriptionDto subscriptionDto) {
        managersService.updatePriceOfSubscriptions(subscriptionDto);
    }


}
