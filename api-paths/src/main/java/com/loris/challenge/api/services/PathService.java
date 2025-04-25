package com.loris.challenge.api.services;

import com.loris.challenge.api.models.dto.PathDTO;

public interface PathService {
    void createPath(Long pathId, PathDTO pathDTO) throws Exception;
}
