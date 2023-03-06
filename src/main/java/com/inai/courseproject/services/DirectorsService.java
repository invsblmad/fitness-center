package com.inai.courseproject.services;

import com.inai.courseproject.dto.StaffDto;
import com.inai.courseproject.models.user.Staff;
import com.inai.courseproject.models.user.User;
import com.inai.courseproject.repositories.StaffRepository;
import com.inai.courseproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorsService {

    private final StaffRepository staffRepository;
    private final UsersRepository usersRepository;

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public void updateSalary(StaffDto staffDto) {
        User user = usersRepository.findByFullName(staffDto.getFullName());
        Optional<Staff> userAsStaff = staffRepository.findByUser(user);
        if (userAsStaff.isPresent()) {
            userAsStaff.get().setSalary(new BigDecimal(staffDto.getSalary()));
            staffRepository.save(userAsStaff.get());
        }
    }
}
