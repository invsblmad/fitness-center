package com.inai.courseproject.services;

import com.inai.courseproject.dto.ClientsSubscriptionDto;
import com.inai.courseproject.dto.StaffDto;
import com.inai.courseproject.dto.UserDto;
import com.inai.courseproject.exceptions.UsernameNotUniqueException;
import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.user.Role;
import com.inai.courseproject.models.user.Staff;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.ClientsSubscriptionsRepository;
import com.inai.courseproject.repositories.StaffRepository;
import com.inai.courseproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ClientsSubscriptionsRepository clientsSubscriptionsRepository;
    private final StaffRepository staffRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_DIRECTOR')")
    public void createStaffAccount(String role, UserDto userDto) {
        if (usersRepository.findByUsername(userDto.getUsername()).isPresent())
            throw new UsernameNotUniqueException("Such username is taken");

        User user = new User(userDto.getFullName(), userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()), Role.valueOf("ROLE_" + role.toUpperCase()));

        usersRepository.save(user);
        staffRepository.save(new Staff(user));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_DIRECTOR')")
    public void deleteStaffAccount(UserDto userDto) {
        User user = usersRepository.findByFullName(userDto.getFullName());
        usersRepository.deleteById(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_DIRECTOR')")
    public List<StaffDto> getStaff(Optional<String> salary, Optional<String> role, Optional<String> fullName) {
        if (role.isPresent())
            return getStaffByRole(role.get());
        else if (salary.isPresent())
            return  getStaffBySalary(salary.get());
        else if (fullName.isPresent())
            return getStaffByFullName(fullName.get());
        else
            return getAllStaff();
    }

    private List<StaffDto> getStaffDto(List<Staff> staff) {
        return staff.stream()
                .filter(s -> !s.getUser().getFullName().equals(authService.getUser().getFullName()))
                .map(s -> new StaffDto(
                        s.getUser().getFullName(), s.getUser().getPhone(), s.getUser().getEmail(),
                        s.getUser().getBirthDate() == null ? "" : s.getUser().getBirthDate().toString(),
                        s.getSalary() == null ? "" : s.getSalary().toString(), s.getAddress())
                ).collect(Collectors.toList());
    }

    private List<StaffDto> getAllStaff() {
        List<Staff> staff = staffRepository.findAll();
        return getStaffDto(staff);
    }

    private List<StaffDto> getStaffByRole(String roleAsString) {
        Role role = Role.valueOf("ROLE_" + roleAsString.toUpperCase());
        List<Staff> staff = staffRepository.findAllByRole(role);
        return getStaffDto(staff);
    }

    private List<StaffDto> getStaffBySalary(String salaryAsString) {
        List<Staff> staff = staffRepository.findAllBySalary(new BigDecimal(salaryAsString));
        return getStaffDto(staff);
    }

    private List<StaffDto> getStaffByFullName(String fullName) {
        List<Staff> staff = staffRepository.findAll();
        return staff.stream()
                .filter(s -> !s.getUser().getFullName().equals(authService.getUser().getFullName()))
                .filter(s -> s.getUser().getFullName().equals(fullName))
                .map(s -> new StaffDto(
                        s.getUser().getFullName(), s.getUser().getPhone(), s.getUser().getEmail(),
                        s.getUser().getBirthDate() == null ? "" : s.getUser().getBirthDate().toString(),
                        s.getSalary() == null ? "" : s.getSalary().toString(), s.getAddress())
                ).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_DIRECTOR')")
    public List<ClientsSubscriptionDto> getClientsSubscriptions(Optional<String> name, Optional<String> firstName,
                                                                Optional<String> lastName) {
        if (name.isPresent())
            return getClientsSubscriptionsByNameOfClass(name.get());
        else if (firstName.isPresent())
            return getClientsSubscriptionsByFirstName(firstName.get());
        else if (lastName.isPresent())
            return getClientsSubscriptionsByLastName(lastName.get());
        else
            return getAllClientsSubscriptions();
    }

    private List<ClientsSubscriptionDto> getAllClientsSubscriptions() {
        List<ClientsSubscription> clientsSubscriptions = clientsSubscriptionsRepository.findAllByActive(true);
        return clientsSubscriptions.stream().map(
                ClientsSubscriptionDto::parseClientsSubscriptionToDto).collect(Collectors.toList());
    }

    private List<ClientsSubscriptionDto> getClientsSubscriptionsByFirstName(String firstName) {
        List<ClientsSubscription> clientsSubscriptions = clientsSubscriptionsRepository.findAllByActive(true);
        return clientsSubscriptions.stream()
                .filter(s -> s.getClient().getFullName().split(" ")[0].equals(firstName))
                .map(ClientsSubscriptionDto::parseClientsSubscriptionToDto)
                .collect(Collectors.toList());
    }

    private List<ClientsSubscriptionDto> getClientsSubscriptionsByLastName(String lastName) {
        List<ClientsSubscription> clientsSubscriptions = clientsSubscriptionsRepository.findAllByActive(true);
        return clientsSubscriptions.stream()
                .filter(s -> s.getClient().getFullName().split(" ")[1].equals(lastName))
                .map(com.inai.courseproject.dto.ClientsSubscriptionDto::parseClientsSubscriptionToDto)
                .collect(Collectors.toList());
    }

    private List<ClientsSubscriptionDto> getClientsSubscriptionsByNameOfClass(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        List<ClientsSubscription> clientsSubscriptions = clientsSubscriptionsRepository.findAllByNameOfClass(name);
        return clientsSubscriptions.stream().map(
                ClientsSubscriptionDto::parseClientsSubscriptionToDto).collect(Collectors.toList());
    }
}
