package com.loris.challenge.api.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loris.challenge.api.models.dto.PathDTO;
import com.loris.challenge.api.models.dto.ShortestPathDTO;
import com.loris.challenge.api.models.dto.StationDTO;
import com.loris.challenge.api.models.entity.Path;
import com.loris.challenge.api.models.entity.Station;
import com.loris.challenge.api.repositories.PathRepository;
import com.loris.challenge.api.repositories.StationRepository;
import com.loris.challenge.api.services.ApiService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StationRepository stationRepository;

    @MockitoBean
    private PathRepository pathRepository;

    @MockitoBean
    private ApiService apiService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateStationSuccess() throws Exception {
        Long stationId = 1L;
        StationDTO stationDTO = new StationDTO();
        stationDTO.setName("Florida");

        when(stationRepository.findById(stationId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/stations/{station_id}", stationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Station created successfully with ID: " + stationId));
    }

    @Test
    public void testCreateStationAlreadyExists() throws Exception {
        Long stationId = 1L;
        StationDTO stationDTO = new StationDTO();
        stationDTO.setName("Florida");

        Station existingStation = new Station();
        existingStation.setId(stationId);
        existingStation.setName("Florida");

        when(stationRepository.findById(stationId)).thenReturn(Optional.of(existingStation));

        mockMvc.perform(put("/stations/{station_id}", stationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Station with ID " + stationId + " already exists"));
    }

    @Test
    public void testCreatePathSuccess() throws Exception {
        Long pathId = 1L;
        PathDTO pathDTO = new PathDTO();
        pathDTO.setCost(10.5);
        pathDTO.setSource_id(1L);
        pathDTO.setDestination_id(2L);

        Station sourceStation = new Station();
        sourceStation.setId(1L);
        sourceStation.setName("Source Station");

        Station destinationStation = new Station();
        destinationStation.setId(2L);
        destinationStation.setName("Destination Station");

        when(pathRepository.findById(pathId)).thenReturn(Optional.empty());
        when(stationRepository.findById(1L)).thenReturn(Optional.of(sourceStation));
        when(stationRepository.findById(2L)).thenReturn(Optional.of(destinationStation));

        mockMvc.perform(put("/paths/{path_id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pathDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Path created successfully with ID: " + pathId));
    }

    @Test
    public void testCreatePathAlreadyExists() throws Exception {
        Long pathId = 1L;
        PathDTO pathDTO = new PathDTO();
        pathDTO.setCost(10.5);
        pathDTO.setSource_id(1L);
        pathDTO.setDestination_id(2L);

        Path existingPath = new Path();
        existingPath.setId(pathId);
        existingPath.setCost(10.5);

        when(pathRepository.findById(pathId)).thenReturn(Optional.of(existingPath));

        mockMvc.perform(put("/paths/{path_id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pathDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Path with ID " + pathId + " already exists"));
    }

    @Test
    public void testCreatePathSourceOrDestinationNotFound() throws Exception {
        Long pathId = 1L;
        PathDTO pathDTO = new PathDTO();
        pathDTO.setCost(10.5);
        pathDTO.setSource_id(1L);
        pathDTO.setDestination_id(2L);

        when(pathRepository.findById(pathId)).thenReturn(Optional.empty());
        when(stationRepository.findById(1L)).thenReturn(Optional.empty());
        when(stationRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/paths/{path_id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pathDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetShortestPathSuccess() throws Exception {
        Long sourceId = 1L;
        Long destinationId = 2L;

        ShortestPathDTO shortestPathDTO = new ShortestPathDTO();
        shortestPathDTO.setPath(List.of(1L, 2L));
        shortestPathDTO.setCost(10.5);

        when(apiService.getShortestPath(sourceId, destinationId)).thenReturn(shortestPathDTO);

        mockMvc.perform(get("/paths/{source_id}/{destination_id}", sourceId, destinationId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"path\":[1,2],\"cost\":10.5}"));
    }   

    @Test
    public void testGetShortestPathError() throws Exception {
        Long sourceId = 1L;
        Long destinationId = 3L;

        when(apiService.getShortestPath(sourceId, destinationId)).thenThrow(new RuntimeException("No path found"));

        mockMvc.perform(get("/paths/{source_id}/{destination_id}", sourceId, destinationId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: No path found"));
    }
}