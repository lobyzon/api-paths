package com.loris.challenge.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loris.challenge.api.models.dto.PathDTO;
import com.loris.challenge.api.models.entity.Path;
import com.loris.challenge.api.repositories.PathRepository;
import com.loris.challenge.api.repositories.StationRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PathServiceImpl implements PathService {
    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void createPath(Long pathId, PathDTO pathDTO) throws Exception {
        Optional<Path> pathOptional = pathRepository.findById(pathId);
        if(pathOptional.isPresent()){
            log.severe("Path with ID " + pathId + " already exists");
            throw new Exception("Path with ID " + pathId + " already exists");
        }
        
        Path path = new Path();
        path.setId(pathId);
        path.setCost(pathDTO.getCost());
        path.setStationSource(stationRepository.findById(pathDTO.getSource_id()).orElseThrow(() -> new Exception("Source station not found")));
        path.setStationDestination(stationRepository.findById(pathDTO.getDestination_id()).orElseThrow(() -> new Exception("Destination station not found")));

        pathRepository.save(path);
    }

}
