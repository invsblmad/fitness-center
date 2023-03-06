package com.inai.courseproject.repositories;

import com.inai.courseproject.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Integer> {

    @Query("select s from Subscription s where s.typeOfFitnessClass.name = :name and s.numberOfClasses = :number and s.isGroup = :isGroup")
    Subscription findSubscription(@Param("name") String name, @Param("number") int number, @Param("isGroup") boolean isGroup);

    @Query("select s from Subscription s where s.typeOfFitnessClass.name = :name and s.numberOfClasses = :numberOfClasses and s.isGroup = :isGroup")
    Subscription findByNumberOfClassesAndGroup(@Param("name") String name, @Param("numberOfClasses") int numberOfClasses, @Param("isGroup") boolean isGroup);
}
