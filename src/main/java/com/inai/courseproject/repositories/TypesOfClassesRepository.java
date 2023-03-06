package com.inai.courseproject.repositories;

import com.inai.courseproject.models.fitnessClass.TypeOfFitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesOfClassesRepository extends JpaRepository<TypeOfFitnessClass, Integer> {

        TypeOfFitnessClass findByName(String name);
}
