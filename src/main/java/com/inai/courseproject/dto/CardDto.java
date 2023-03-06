package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDto {
    private String cardNumber;
    private String name;
    private String expiredDate;
    private String securityCode;
}
