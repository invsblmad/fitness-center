package com.inai.courseproject.models;

import com.inai.courseproject.models.fitnessClass.TypeOfFitnessClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_type_of_fitness_class", referencedColumnName = "id")
    private TypeOfFitnessClass typeOfFitnessClass;

    @Column(name = "number_of_classes")
    private int numberOfClasses;

    @Column(name = "is_group")
    private boolean isGroup;

    private BigDecimal price;

    public String getIsGroupOrNot() {
        if (this.isGroup()) return "group";
        else return "individual";
    }

}
