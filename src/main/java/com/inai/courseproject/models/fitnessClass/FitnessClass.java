package com.inai.courseproject.models.fitnessClass;

import com.inai.courseproject.models.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "fitness_classes")
@Data
@NoArgsConstructor
public class FitnessClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_coach", referencedColumnName = "id")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "id_type", referencedColumnName = "id")
    private TypeOfFitnessClass type;

    @ManyToOne
    @JoinColumn(name = "id_location", referencedColumnName = "id")
    private LocationOfFitnessClass location;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private Days days;

    @Column(name = "is_group")
    private boolean isGroup;

    public FitnessClass(User coach, TypeOfFitnessClass type, LocationOfFitnessClass location, LocalTime time, Days days, boolean isGroup) {
        this.coach = coach;
        this.type = type;
        this.location = location;
        this.time = time;
        this.days = days;
        this.isGroup = isGroup;
    }

    public String getIsGroupOrNot() {
        if (this.isGroup()) return "group";
        else return "individual";
    }

    public String getDaysAsString() {
        if (this.days == Days.ODD) return "Mon, Wed, Fri";
        else return "Tue, Thu, Sat";
    }

    public void update(User coach, TypeOfFitnessClass type, LocationOfFitnessClass location, LocalTime time, boolean isGroup) {
        this.coach = coach;
        this.type = type;
        this.location = location;
        this.time = time;
        this.isGroup = isGroup;
    }
}
