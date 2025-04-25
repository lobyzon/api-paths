package com.loris.challenge.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loris.challenge.api.models.dto.PathDTO;
import com.loris.challenge.api.models.dto.ShortestPathDTO;
import com.loris.challenge.api.models.dto.StationDTO;
import com.loris.challenge.api.services.ApiService;
import com.loris.challenge.api.services.PathService;
import com.loris.challenge.api.services.StationService;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

@RestController
@Log
public class ApiController {
    private static final String STATION_ID_MESSAGE = "Station ID and name cannot be null";
    private static final String CREATE_PATH_MESSAGE = "Path ID, source station ID, destination station ID and cost cannot be null";
    private static final String SHORTEST_PATH_MESSAGE = "Source and destination station IDs cannot be null";

    @Autowired
    private StationService stationService;

    @Autowired
    private PathService pathService;

    @Autowired
    private ApiService apiService;

    @PutMapping("/stations/{station_id}")
    public ResponseEntity<?> createStation(@PathVariable Long station_id, @Valid @RequestBody StationDTO stationDTO) {
        if(station_id == null || stationDTO == null) {
            log.severe(STATION_ID_MESSAGE);
            return ResponseEntity.badRequest().body(STATION_ID_MESSAGE);
        }
        try {
            stationService.createStation(station_id, stationDTO);
        } catch (Exception e) {
            log.severe("Error creating station: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }        
        
        return ResponseEntity.ok("Station created successfully with ID: " + station_id);
    }

    @PutMapping("/paths/{path_id}")
    public ResponseEntity<?> createPath(@PathVariable Long path_id, @Valid @RequestBody PathDTO pathDTO) {
        if(path_id == null) {
            log.severe(CREATE_PATH_MESSAGE);
            return ResponseEntity.badRequest().body(CREATE_PATH_MESSAGE);
        }
        
        try {
            pathService.createPath(path_id, pathDTO);
        } catch (Exception e) {
            log.severe("Error creating path: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

        return ResponseEntity.ok("Path created successfully with ID: " + path_id);
    }

    @GetMapping("/paths/{source_id}/{destination_id}")
    public ResponseEntity<?> getShortestPath(@PathVariable Long source_id, @PathVariable Long destination_id) {
        if(source_id == null || destination_id == null) {
            log.severe(SHORTEST_PATH_MESSAGE);
            return ResponseEntity.badRequest().body(SHORTEST_PATH_MESSAGE);
        }
        
        ShortestPathDTO shortestPathDTO = null;
        try {
            shortestPathDTO = apiService.getShortestPath(source_id, destination_id);
        } catch (Exception e) {
            log.severe("Error calculating shortest path: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok(shortestPathDTO);
    }
}