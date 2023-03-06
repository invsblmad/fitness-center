package com.inai.courseproject.dto;

import com.inai.courseproject.models.user.ClientsParameters;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientsParametersDto {

    private String fullName;
    private String height;
    private String weight;
    private String bia;
    private String chestGirth;
    private String waistGirth;
    private String hipGirth;

    public static ClientsParametersDto parseClientsParametersToDto(ClientsParameters parameters) {
        return new ClientsParametersDto(
                parameters.getClient().getFullName(),
                String.valueOf(parameters.getHeight()),
                String.valueOf(parameters.getWeight()),
                String.valueOf(parameters.getBia()),
                String.valueOf(parameters.getChestGirth()),
                String.valueOf(parameters.getWaistGirth()),
                String.valueOf(parameters.getHipGirth())
        );
    }
}
