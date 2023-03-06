package com.inai.courseproject.models;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "id_fitness_class", referencedColumnName = "id")
    private FitnessClass fitnessClass;

    @Column(name = "is_frozen")
    private boolean isFrozen;

    @Column(name = "is_attended")
    private boolean isAttended;
}
