package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RatingEntity;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void findAllRatings_ShouldReturnListOfRatings() {

        // Arrange
        RatingEntity rating1 = new RatingEntity();
        RatingEntity rating2 = new RatingEntity();
        when(ratingRepository.findAll()).thenReturn(List.of(rating1, rating2));

        // Act
        List<RatingEntity> result = ratingService.findAllRatings();

        // Assert
        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void saveRating_ShouldCallRepositorySave() {

        // Arrange
        RatingEntity rating = new RatingEntity();

        // Act
        ratingService.saveRating(rating);

        // Assert
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void checkIfRatingExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(ratingRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = ratingService.checkIfRatingExists(1);

        // Assert
        assertTrue(exists);
        verify(ratingRepository).existsById(1);
    }

    @Test
    void checkIfRatingExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(ratingRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = ratingService.checkIfRatingExists(2);

        // Assert
        assertFalse(exists);
        verify(ratingRepository).existsById(2);
    }

    @Test
    void findRatingById_ShouldReturnRating_WhenFound() {

        // Arrange
        RatingEntity rating = new RatingEntity();
        rating.setId(1);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // Act
        RatingEntity result = ratingService.findRatingById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(ratingRepository).findById(1);
    }

    @Test
    void findRatingById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(ratingRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        RatingEntity result = ratingService.findRatingById(999);

        // Assert
        assertNull(result);
        verify(ratingRepository).findById(999);
    }

    @Test
    void deleteRating_ShouldCallRepositoryDeleteById() {

        // Act
        ratingService.deleteRating(1);

        // Assert
        verify(ratingRepository, times(1)).deleteById(1);
    }
}
