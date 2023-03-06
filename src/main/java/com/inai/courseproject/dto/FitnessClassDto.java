package com.inai.courseproject.dto;

import com.inai.courseproject.models.ClientsSubscription;
import com.inai.courseproject.models.fitnessClass.FitnessClass;
import com.inai.courseproject.repositories.ClientsSubscriptionsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class FitnessClassDto {

    private String nameOfCoach;
    private String name;
    private String type;
    private String location;
    private String time;
    private int numberOfVacantPlaces;

    public static String getTimeRangeOfClass(FitnessClass fitnessClass) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = fitnessClass.getTime();
        return startTime.format(dtf) + "-" + startTime.plusMinutes(fitnessClass.getType().getDuration()).format(dtf);
    }

    public static FitnessClassDto parseFitnessClassToDto(FitnessClass f, int numberOfVacantPlaces) {
        return new FitnessClassDto(
                f.getCoach().getFullName(),
                f.getType().getName(),
                f.getIsGroupOrNot(),
                f.getLocation().getName(),
                getTimeRangeOfClass(f),
                numberOfVacantPlaces
        );
    }

    public static boolean isGroup(String type) {
        return type.equals("group");
    }

}
