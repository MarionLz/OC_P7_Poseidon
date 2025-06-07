package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

}
