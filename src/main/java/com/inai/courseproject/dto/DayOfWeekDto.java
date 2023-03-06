package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayOfWeekDto {
    private String dayOfWeek;
    private int dayOfMonth;
    private boolean isToday;
}
