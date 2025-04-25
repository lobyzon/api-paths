package com.loris.challenge.api.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "path")
@Getter
@Setter
public class Path {
    @Id    
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_source_id", referencedColumnName = "id")
    private Station stationSource;

    @ManyToOne
    @JoinColumn(name = "station_destination_id", referencedColumnName = "id")
    private Station stationDestination;

    private Double cost;
}
