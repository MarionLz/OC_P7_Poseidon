package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
