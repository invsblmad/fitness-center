package com.inai.courseproject.models.user;

import com.inai.courseproject.models.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients_parameters")
@Data
@NoArgsConstructor
public class ClientsParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private User client;

    @Column(name = "set_date")
    private LocalDate setDate;

    private Double height;
    private Double weight;
    private Double bia;

    @Column(name = "chest_girth")
    private Double chestGirth;

    @Column(name = "waist_girth")
    private Double waistGirth;

    @Column(name = "hip_girth")
    private Double hipGirth;

    public ClientsParameters(User client, LocalDate setDate, Double height, Double weight, Double bia, Double chestGirth, Double waistGirth, Double hipGirth) {
        this.client = client;
        this.setDate = setDate;
        this.height = height;
        this.weight = weight;
        this.bia = bia;
        this.chestGirth = chestGirth;
        this.waistGirth = waistGirth;
        this.hipGirth = hipGirth;
    }

    public ClientsParameters(User client, LocalDate setDate, Double weight, Double bia, Double chestGirth, Double waistGirth, Double hipGirth) {
        this.client = client;
        this.setDate = setDate;
        this.weight = weight;
        this.bia = bia;
        this.chestGirth = chestGirth;
        this.waistGirth = waistGirth;
        this.hipGirth = hipGirth;
    }

}
