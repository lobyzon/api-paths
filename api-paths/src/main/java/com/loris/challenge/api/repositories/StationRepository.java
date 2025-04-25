package com.loris.challenge.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loris.challenge.api.models.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long>{

}
