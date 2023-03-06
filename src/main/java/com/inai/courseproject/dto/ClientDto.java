package com.inai.courseproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

    private int id;
    private String fullName;
    private boolean isAttended;
    private boolean isFrozen;
}
