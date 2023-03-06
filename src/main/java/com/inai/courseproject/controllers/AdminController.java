package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.dto.StaffDto;
import com.inai.courseproject.dto.UserDto;
import com.inai.courseproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/staff")
    public List<StaffDto> getStaff(@RequestParam(value = "salary") Optional<String> salary,
                                   @RequestParam(value = "role") Optional<String> role,
                                   @RequestParam(value = "name") Optional<String> fullName) {
        return adminService.getStaff(salary, role, fullName);
    }

    @PostMapping("/add/{role}")
    public void createStaffAccount(@PathVariable String role, @RequestBody UserDto userDto) {
        adminService.createStaffAccount(role, userDto);
    }

    @DeleteMapping("/delete-staff")
    public void deleteStaffAccount(@RequestBody UserDto userDto) {
        adminService.deleteStaffAccount(userDto);
    }

    @GetMapping("/clients")
    public List<ClientsSubscriptionDto> getClientsSubscriptions(@RequestParam(value = "name") Optional<String> name,
                                                                @RequestParam(value = "firstname") Optional<String> firstName,
                                                                @RequestParam(value = "lastname") Optional<String> lastName) {
        return adminService.getClientsSubscriptions(name, firstName, lastName);
    }
}
