package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.*;
import com.inai.courseproject.services.CoachesService;
import com.inai.courseproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coaches")
public class CoachesController {

    private final CoachesService coachesService;
    private final UsersService usersService;

    @GetMapping()
    public String greet() {
        return usersService.greet();
    }

    @GetMapping("/my-account")
    public StaffAccountDto getCoachesInfo() {
        return usersService.getStaffInfo();
    }

    @PutMapping("/my-account")
    public void updateCoachesInfo(@RequestBody StaffAccountDto staffAccountDto) {
        usersService.updateStaffInfo(staffAccountDto);
    }

    @GetMapping("/my-schedule/today")
    public List<CoachesFitnessClassDto> getFitnessClassesForToday(@RequestParam(value = "name") Optional<String> name,
                                                                  @RequestParam(value = "type") Optional<String> type,
                                                                  @RequestParam(value = "time") Optional<String> time) {
        return coachesService.getFitnessClassesForToday(name, type, time);
    }

    @GetMapping("/my-schedule/{day}")
    public List<CoachesFitnessClassDto> getFitnessClasses(@PathVariable String day, @RequestParam(value = "name") Optional<String> name,
                                                          @RequestParam(value = "type") Optional<String> type,
                                                          @RequestParam(value = "time") Optional<String> time) {
        return coachesService.getFitnessClasses(day, name, type, time);
    }

    @GetMapping("/my-clients")
    public List<ClientsParametersDto> getClientsParameters(@RequestParam(value = "month")Optional<String> month,
                                                      @RequestParam(value = "year") Optional<String> year) {
        return coachesService.getClientsParameters(month, year);
    }

}
