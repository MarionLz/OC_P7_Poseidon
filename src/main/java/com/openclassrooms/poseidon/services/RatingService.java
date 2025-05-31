package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RatingEntity;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<RatingEntity> findAllRatings() {
        return ratingRepository.findAll();
    }

    public void saveRating(RatingEntity rating) {
        ratingRepository.save(rating);
    }

    public boolean checkIfRatingExists(Integer id) {
        return ratingRepository.existsById(id);
    }

    public RatingEntity findRatingById(Integer id) {
        return ratingRepository.findById(id).orElse(null);
    }

    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }
}
