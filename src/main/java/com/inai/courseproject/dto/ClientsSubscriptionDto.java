package com.inai.courseproject.dto;

import com.inai.courseproject.models.ClientsSubscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsSubscriptionDto {

    private String clientsFullName;
    private String phone;
    private String email;

    private String nameOfFitnessClass;
    private int numberOfClasses;
    private String typeOfFitnessClass;

    private String coachesFullName;
    private String time;
    private String days;
    private String startDate;

    public static ClientsSubscriptionDto parseClientsSubscriptionToDto(ClientsSubscription s) {
        return new ClientsSubscriptionDto(
                s.getClient().getFullName(),
                s.getClient().getPhone(),
                s.getClient().getEmail(),
                s.getSubscription().getTypeOfFitnessClass().getName(),
                s.getSubscription().getNumberOfClasses(),
                s.getFitnessClass().getIsGroupOrNot(),
                s.getFitnessClass().getCoach().getFullName(),
                s.getFitnessClass().getTime().toString(),
                s.getFitnessClass().getDaysAsString(),
                s.getStartDate().toString()
        );
    }


}
