package com.inai.courseproject.dto;

import com.inai.courseproject.models.user.ClientsParameters;
import com.inai.courseproject.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonalParametersDto {

    private String height;
    private String weight;
    private String bia;
    private String chestGirth;
    private String waistGirth;
    private String hipGirth;

    public static PersonalParametersDto parseClientsParametersToDto(ClientsParameters parameters) {
        return new PersonalParametersDto(
                String.valueOf(parameters.getHeight()),
                String.valueOf(parameters.getWeight()),
                String.valueOf(parameters.getBia()),
                String.valueOf(parameters.getChestGirth()),
                String.valueOf(parameters.getWaistGirth()),
                String.valueOf(parameters.getHipGirth())
        );
    }

    public static ClientsParameters parseDtoToClientsParameters(PersonalParametersDto parameters, LocalDate setDate, User client) {
        return new ClientsParameters(
                client, setDate,
                Double.valueOf(parameters.getHeight()),
                Double.valueOf(parameters.getWeight()),
                Double.valueOf(parameters.getBia()),
                Double.valueOf(parameters.getChestGirth()),
                Double.valueOf(parameters.getWaistGirth()),
                Double.valueOf(parameters.getHipGirth())
        );
    }
}
