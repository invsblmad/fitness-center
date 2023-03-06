package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeOfClassDto {

    private String name;
    private String description;
    private int duration;
    private String priceRange;
}
