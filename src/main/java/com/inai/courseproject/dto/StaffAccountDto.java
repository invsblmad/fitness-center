package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAccountDto {

    private UserAccountDto userInfo;
    private String salary;
    private String address;

    public StaffAccountDto(UserAccountDto userInfo) {
        this.userInfo = userInfo;
    }
}
