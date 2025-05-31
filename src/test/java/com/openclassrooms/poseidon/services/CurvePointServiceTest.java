package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    void findAllCurvePoints_ShouldReturnListOfCurvePoints() {

        // Arrange
        CurvePointEntity curvePoint1 = new CurvePointEntity();
        CurvePointEntity curvePoint2 = new CurvePointEntity();
        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint1, curvePoint2));

        // Act
        List<CurvePointEntity> result = curvePointService.findAllCurvePoints();

        // Assert
        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void saveCurvePoint_ShouldCallRepositorySave() {

        // Arrange
        CurvePointEntity curvePoint = new CurvePointEntity();

        // Act
        curvePointService.saveCurvePoint(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void checkIfCurvePointExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(curvePointRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = curvePointService.checkIfCurvePointExists(1);

        // Assert
        assertTrue(exists);
        verify(curvePointRepository).existsById(1);
    }

    @Test
    void checkIfCurvePointExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(curvePointRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = curvePointService.checkIfCurvePointExists(2);

        // Assert
        assertFalse(exists);
        verify(curvePointRepository).existsById(2);
    }

    @Test
    void findCurvePointById_ShouldReturnCurvePoint_WhenFound() {

        // Arrange
        CurvePointEntity curvePoint = new CurvePointEntity();
        curvePoint.setId(1);
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // Act
        CurvePointEntity result = curvePointService.findCurvePointById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(curvePointRepository).findById(1);
    }

    @Test
    void findCurvePointById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(curvePointRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        CurvePointEntity result = curvePointService.findCurvePointById(999);

        // Assert
        assertNull(result);
        verify(curvePointRepository).findById(999);
    }

    @Test
    void deleteCurvePoint_ShouldCallRepositoryDeleteById() {

        // Act
        curvePointService.deleteCurvePoint(1);

        // Assert
        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
