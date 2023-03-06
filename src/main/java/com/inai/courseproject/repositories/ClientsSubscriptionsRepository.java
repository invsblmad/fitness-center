package com.inai.courseproject.repositories;

import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.fitnessClass.Days;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ClientsSubscriptionsRepository extends JpaRepository<ClientsSubscription, Integer> {

    @Query("select s from ClientsSubscription s where s.client = :client and s.isActive = :isActive")
    List<ClientsSubscription> findAllByClient(@Param("client")  User client, @Param("isActive")  boolean isActive);

    List<ClientsSubscription> findByFitnessClass(FitnessClass fitnessClass);

//    @Query("select s from ClientsSubscription s where s.fitnessClass = :finessClass and s.isActive = :isActive")
//    List<ClientsSubscription> findByFitnessClassAndActive(@Param("fitnessClass")  FitnessClass fitnessClass, @Param("isActive")  boolean isActive);

    @Query("select s from ClientsSubscription s where s.client = :client and s.isActive = :isActive and s.fitnessClass.days = :days")
    List<ClientsSubscription> findAllByClientAndDaysOfClass(@Param("client")  User client, @Param("isActive")  boolean isActive,
                                                            @Param("days") Days days);

    @Query("select s from ClientsSubscription s where s.client = :client and s.isActive = :isActive and s.fitnessClass.days = :days and s.fitnessClass.type.name = :name")
    List<ClientsSubscription> findAllByClientAndDaysAndNameOfClass(@Param("client")  User client, @Param("isActive")  boolean isActive,
                                                                   @Param("days") Days days, @Param("name") String name);

    @Query("select s from ClientsSubscription s where s.client = :client and s.isActive = :isActive and s.fitnessClass.days = :days and s.fitnessClass.isGroup = :isGroup")
    List<ClientsSubscription> findAllByClientAndDaysAndTypeOfClass(@Param("client")  User client, @Param("isActive")  boolean isActive,
                                                                   @Param("days") Days days, @Param("isGroup") boolean isGroup);

    @Query("select s from ClientsSubscription s where s.client = :client and s.isActive = :isActive and s.fitnessClass.days = :days and s.fitnessClass.time = :time")
    List<ClientsSubscription> findAllByClientAndDaysAndTimeOfClass(@Param("client")  User client, @Param("isActive")  boolean isActive,
                                                                   @Param("days") Days days, @Param("time") LocalTime time);

    @Query("select s from ClientsSubscription s where s.isActive = :isActive")
    List<ClientsSubscription> findAllByActive(@Param("isActive") boolean isActive);

    @Query("select s from ClientsSubscription s where s.subscription.typeOfFitnessClass.name = :name")
    List<ClientsSubscription> findAllByNameOfClass(@Param("name") String name);

    @Query("select s from ClientsSubscription s where s.fitnessClass.coach = :coach")
    List<ClientsSubscription> findAllByCoach(@Param("coach") User coach);
}
