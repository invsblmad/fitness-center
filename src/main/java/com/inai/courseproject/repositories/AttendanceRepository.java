package com.inai.courseproject.repositories;

import com.inai.courseproject.models.Attendance;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("select a from Attendance a where a.fitnessClass = :fitnessClass and a.date = :date")
    Attendance findByFitnessClassAndDate(@Param("fitnessClass") FitnessClass fitnessClass, @Param("date") LocalDate date);
}
