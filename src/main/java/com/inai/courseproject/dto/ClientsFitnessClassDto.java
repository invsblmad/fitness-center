package com.inai.courseproject.dto;

import com.inai.courseproject.models.fitnessClass.FitnessClass;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class ClientsFitnessClassDto {

    private String nameOfCoach;
    private String name;
    private String type;
    private String location;
    private String time;

    public static ClientsFitnessClassDto parseFitnessClassToDto(FitnessClass f) {
        return new ClientsFitnessClassDto(
                f.getCoach().getFullName(),
                f.getType().getName(),
                f.getIsGroupOrNot(),
                f.getLocation().getName(),
                FitnessClassDto.getTimeRangeOfClass(f)
        );
    }

    public static List<ClientsFitnessClassDto> parseFitnessClassesToDtos(List<FitnessClass> fitnessClasses) {
        return fitnessClasses.stream().map(
                ClientsFitnessClassDto::parseFitnessClassToDto
        ).collect(Collectors.toList());
    }
}
