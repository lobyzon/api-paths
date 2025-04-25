package com.loris.challenge.api.services;

import com.loris.challenge.api.models.dto.StationDTO;

public interface StationService {
    void createStation(Long stationId, StationDTO stationDTO) throws Exception;
}
