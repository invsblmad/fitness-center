package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccountDto {

    private String fullName;
    private String phone;
    private String email;
    private String username;
    private String birthDate;
}
