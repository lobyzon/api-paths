package com.loris.challenge.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationDTO {
    @NotBlank(message = "El nombre de la estacion no puede ser nulo")
    private String name;
}
