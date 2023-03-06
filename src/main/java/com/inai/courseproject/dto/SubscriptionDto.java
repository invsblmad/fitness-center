package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SubscriptionDto {

    private String nameOfFitnessClass;
    private int numberOfClasses;
    private String typeOfFitnessClass;
    private String price;

    public boolean isGroup() {
        return this.typeOfFitnessClass.equals("group");
    }
}

