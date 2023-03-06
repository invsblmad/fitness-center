package com.inai.courseproject.dto;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachesFitnessClassDto {

    private String name;
    private String type;
    private String location;
    private String time;
    private int numberOfPeopleInGroup;

    public static CoachesFitnessClassDto parseFitnessClassToDto(FitnessClass f, int numberOfPeopleInGroup) {
        return new CoachesFitnessClassDto(
                f.getType().getName(),
                f.getIsGroupOrNot(),
                f.getLocation().getName(),
                FitnessClassDto.getTimeRangeOfClass(f),
                numberOfPeopleInGroup
        );
    }
}
