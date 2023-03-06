package com.inai.courseproject.models.user;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    @OneToOne(mappedBy = "coach", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CoachesPortfolio coachesPortfolio;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FitnessClass> fitnessClasses;

    private String email;
    private String phone;

    public User(String fullName, String username, String password, Role role) {
        setCredentials(username, password);
        this.fullName = fullName;
        this.role = role;
    }

    public User(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updateInfo(String fullName, String username, String email, String phone, LocalDate birthDate) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
    }
}
