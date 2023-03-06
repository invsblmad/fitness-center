package com.inai.courseproject.services;

import com.inai.courseproject.dto.StaffAccountDto;
import com.inai.courseproject.dto.UserAccountDto;
import com.inai.courseproject.exceptions.UsernameNotUniqueException;
import com.inai.courseproject.models.user.Staff;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.StaffRepository;
import com.inai.courseproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final AuthService authService;

    private final UsersRepository usersRepository;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final StaffRepository staffRepository;

    public String greet() {
        return "Hello, " + authService.getUser().getFullName();
    }

    private UserAccountDto getUserAccountDto(User user) {
        return new UserAccountDto(user.getFullName(), user.getPhone(), user.getEmail(),
                user.getUsername(), user.getBirthDate() == null ? "" : user.getBirthDate().toString());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_DIRECTOR')")
    public UserAccountDto getUsersInfo() {
        User user = authService.getUser();
        return getUserAccountDto(user);
    }

    private void checkUsername(String oldUsername, String newUsername) {
        if (!oldUsername.equals(newUsername) && usersRepository.findByUsername(newUsername).isPresent())
            throw new UsernameNotUniqueException("Such username is taken");
    }

    private void updateUser(User user, UserAccountDto userDto) {
        checkUsername(user.getUsername(), userDto.getUsername());
        if (userDto.getBirthDate().equals(""))
            user.updateInfo(userDto.getFullName(), userDto.getUsername(), userDto.getEmail(), userDto.getPhone(), null);
        else
            user.updateInfo(userDto.getFullName(), userDto.getUsername(), userDto.getEmail(), userDto.getPhone(), LocalDate.parse(userDto.getBirthDate(), dtf));
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_DIRECTOR')")
    public void updateUsersInfo(UserAccountDto userDto) {
        User user = authService.getUser();
        updateUser(user, userDto);
        usersRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_COACH')")
    public StaffAccountDto getStaffInfo() {
        UserAccountDto userAccountDto = getUserAccountDto(authService.getUser());
        Optional<Staff> staff = staffRepository.findByUser(authService.getUser());
        return staff.map(value -> new StaffAccountDto(userAccountDto, value.getSalary() == null ? "" : value.getSalary().toString(),
                value.getAddress())).orElseGet(() -> new StaffAccountDto(userAccountDto));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_COACH')")
    public void updateStaffInfo(StaffAccountDto staffDto) {
        User user = authService.getUser();
        updateUser(user, staffDto.getUserInfo());
        usersRepository.save(user);

        Staff staff;
        if (staffRepository.findByUser(user).isPresent()) staff = staffRepository.findByUser(user).get();
        else staff = new Staff(user);

        staff.setAddress(staffDto.getAddress());
        staffRepository.save(staff);
    }
}
