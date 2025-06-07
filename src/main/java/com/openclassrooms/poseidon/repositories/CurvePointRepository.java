package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePointEntity, Integer> {

}
