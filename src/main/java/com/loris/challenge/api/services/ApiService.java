package com.loris.challenge.api.services;

import com.loris.challenge.api.models.dto.ShortestPathDTO;

public interface ApiService {
    ShortestPathDTO getShortestPath(Long sourceId, Long destinationId);    
}
