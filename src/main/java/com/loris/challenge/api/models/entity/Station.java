package com.loris.challenge.api.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "station")
@Getter
@Setter
public class Station {
    @Id    
    private Long id;
    
    private String name;
}
