package com.inai.courseproject.repositories;

import com.inai.courseproject.models.fitnessClass.LocationOfFitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsOfClassesRepository extends JpaRepository<LocationOfFitnessClass, Integer> {
    LocationOfFitnessClass findByName(String name);
}
