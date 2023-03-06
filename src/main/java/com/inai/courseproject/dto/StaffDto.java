package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffDto {

    private String fullName;
    private String phone;
    private String email;
    private String birthDate;
    private String salary;
    private String address;


}
