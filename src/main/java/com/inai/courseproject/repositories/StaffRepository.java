package com.inai.courseproject.repositories;

import com.inai.courseproject.models.user.Role;
import com.inai.courseproject.models.user.Staff;
import com.inai.courseproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query("select s from Staff s order by s.salary")
    List<Staff> findAll();

    @Query("select s from Staff s where s.user.role = :role order by s.salary")
    List<Staff> findAllByRole(Role role);

    List<Staff> findAllBySalary(BigDecimal salary);

    Optional<Staff> findByUser(User user);
}
