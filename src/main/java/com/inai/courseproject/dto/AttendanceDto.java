package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AttendanceDto {

    private String name;
    private String type;
    private String location;
    private String time;
    private List<ClientDto> clients;

    public AttendanceDto(String name, String type, String location, String time) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.time = time;
    }
}
