package com.loris.challenge.api.models.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortestPathDTO {
    
    private List<Long> path;
    private Double cost;
}