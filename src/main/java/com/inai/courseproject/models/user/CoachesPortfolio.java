package com.inai.courseproject.models.user;

import com.inai.courseproject.models.fitnessClass.TypeOfFitnessClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "coaches_portfolios")
@Data
@NoArgsConstructor
public class CoachesPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_coach", referencedColumnName = "id")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "id_type_of_fitness_class", referencedColumnName = "id")
    private TypeOfFitnessClass typeOfFitnessClass;

    @Column(name = "work_experience")
    private int workExperience;

    private String information;
    private String image;
}
