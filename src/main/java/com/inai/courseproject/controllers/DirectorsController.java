package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.dto.StaffDto;
import com.inai.courseproject.dto.UserAccountDto;
import com.inai.courseproject.dto.UserDto;
import com.inai.courseproject.services.AdminService;
import com.inai.courseproject.services.DirectorsService;
import com.inai.courseproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorsController {

    private final UsersService usersService;
    private final AdminService adminService;
    private final DirectorsService directorsService;

    @GetMapping()
    public String greet() {
        return usersService.greet();
    }

    @GetMapping("/my-account")
    public UserAccountDto getDirectorsInfo() {
        return usersService.getUsersInfo();
    }

    @PutMapping("/my-account")
    public void updateDirectorsInfo(@RequestBody UserAccountDto userAccountDto) {
        usersService.updateUsersInfo(userAccountDto);
    }

    @PutMapping("/staff")
    public void updateSalary(@RequestBody StaffDto staffDto) {
        directorsService.updateSalary(staffDto);
    }

}
