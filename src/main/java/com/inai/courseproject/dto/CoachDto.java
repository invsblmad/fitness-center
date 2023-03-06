package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachDto {

    private String fullName;
    private String typeOfFitnessClass;
    private int workExperience;
    private String information;
}
