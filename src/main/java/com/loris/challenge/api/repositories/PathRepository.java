package com.loris.challenge.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.loris.challenge.api.models.entity.Path;

public interface PathRepository extends JpaRepository<Path, Long>{

}
