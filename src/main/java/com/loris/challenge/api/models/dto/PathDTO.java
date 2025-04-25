package com.loris.challenge.api.models.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathDTO {
    
    @Positive(message = "El costo debe ser mayor a cero")
    private double cost;
    @Positive(message = "El id de la estacion origen debe ser mayor a cero")    
    private Long source_id;
    @Positive(message = "El id de la estacion destino debe ser mayor a cero")
    private Long destination_id;
}
