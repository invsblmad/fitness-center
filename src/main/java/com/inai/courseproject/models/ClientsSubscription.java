package com.inai.courseproject.models;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.models.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clients_subscriptions")
@Data
@NoArgsConstructor
public class ClientsSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "id_subscription", referencedColumnName = "id")
    private Subscription subscription;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "id_fitness_class", referencedColumnName = "id")
    private FitnessClass fitnessClass;

    @Column(name = "is_active")
    private boolean isActive;


    public ClientsSubscription(User client, Subscription subscription, LocalDate purchaseDate, LocalDate startDate, FitnessClass fitnessClass, boolean isActive) {
        this.client = client;
        this.subscription = subscription;
        this.purchaseDate = purchaseDate;
        this.startDate = startDate;
        this.fitnessClass = fitnessClass;
        this.isActive = isActive;
    }
}
