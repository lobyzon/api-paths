package com.loris.challenge.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loris.challenge.api.models.dto.StationDTO;
import com.loris.challenge.api.models.entity.Station;
import com.loris.challenge.api.repositories.StationRepository;

import lombok.extern.java.Log;

@Service
@Log
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void createStation(Long stationId, StationDTO stationDTO) throws Exception {
        Optional<Station> stationOptional = stationRepository.findById(stationId);
        if(stationOptional.isPresent()){
            log.severe("Station with ID " + stationId + " already exists");
            throw new Exception("Station with ID " + stationId + " already exists");
        }
        
        Station station = new Station();
        station.setId(stationId);
        station.setName(stationDTO.getName());
        stationRepository.save(station);
    }

}
