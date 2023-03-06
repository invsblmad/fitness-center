package com.inai.courseproject.repositories;

import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FitnessClassesRepository extends JpaRepository<FitnessClass, Integer> {

    List<FitnessClass> findAllByDays(Days days);

    @Query("select f from FitnessClass f where f.days = :days and f.type.name = :name")
    List<FitnessClass> findAllByDaysAndName(@Param("days") Days days, @Param("name") String name);

    @Query("select f from FitnessClass f where f.days = :days and f.isGroup = :isGroup")
    List<FitnessClass> findAllByDaysAndType(@Param("days") Days days, @Param("isGroup") boolean isGroup);

    List<FitnessClass> findAllByDaysAndTime(Days days, LocalTime time);

    @Query("select f from FitnessClass f where f.coach.fullName = :coachesName and f.type.name = :name " +
            "and f.isGroup = :isGroup and f.time = :time and f.days = :days")
    Optional<FitnessClass> findFitnessClass(@Param("coachesName") String coachesName, @Param("name") String name,
                              @Param("isGroup") boolean isGroup, @Param("time") LocalTime time, @Param("days") Days days);

    List<FitnessClass> findAllByCoachAndDays(User coach, Days days);

    @Query("select f from FitnessClass f where f.coach = :coach and f.days = :days and f.type.name = :name")
    List<FitnessClass> findAllByCoachAndDaysAndName(@Param("coach") User coach, @Param("days") Days days, @Param("name") String name);

    @Query("select f from FitnessClass f where f.coach = :coach and f.days = :days and f.isGroup = :isGroup")
    List<FitnessClass> findAllByCoachAndDaysAndType(@Param("coach") User coach, @Param("days") Days days, @Param("isGroup") boolean isGroup);

    List<FitnessClass> findAllByCoachAndDaysAndTime(User coach, Days days, LocalTime time);

}
