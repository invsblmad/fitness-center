package com.inai.courseproject.models.fitnessClass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "types_of_fitness_classes")
@Data
@NoArgsConstructor
public class TypeOfFitnessClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int duration;

    public TypeOfFitnessClass(String name) {
        this.name = name;
    }
}
