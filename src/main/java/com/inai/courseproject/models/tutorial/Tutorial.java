package com.inai.courseproject.models.tutorial;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@Data
@NoArgsConstructor
public class Tutorial {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String link;

    @Enumerated(EnumType.STRING)
    private TutorialType type;

    @ManyToOne
    @JoinColumn(name = "id_fitness_class", referencedColumnName = "id")
    private FitnessClass fitnessClass;
}
