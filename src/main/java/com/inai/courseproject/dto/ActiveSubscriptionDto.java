package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveSubscriptionDto {

    private String nameOfFitnessClass;
    private int numberOfClasses;
    private String typeOfFitnessClass;

    private String coachesFullName;
    private String time;
    private String days;
    private String startDate;
}
