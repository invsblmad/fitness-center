package com.inai.courseproject.models.fitnessClass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "locations_of_fitness_classes")
@Data
@NoArgsConstructor
public class LocationOfFitnessClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
