package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    void findAllBids_ShouldReturnListOfBids() {

        // Arrange
        BidListEntity bid1 = new BidListEntity();
        BidListEntity bid2 = new BidListEntity();
        when(bidListRepository.findAll()).thenReturn(List.of(bid1, bid2));

        // Act
        List<BidListEntity> result = bidListService.findAllBids();

        // Assert
        assertEquals(2, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void saveBid_ShouldCallRepositorySave() {

        // Arrange
        BidListEntity bid = new BidListEntity();
        bid.setAccount("TestAccount");

        // Act
        bidListService.saveBid(bid);

        // Assert
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    void checkIfBidExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(bidListRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = bidListService.checkIfBidExists(1);

        // Assert
        assertTrue(exists);
        verify(bidListRepository).existsById(1);
    }

    @Test
    void checkIfBidExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(bidListRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = bidListService.checkIfBidExists(2);

        // Assert
        assertFalse(exists);
        verify(bidListRepository).existsById(2);
    }

    @Test
    void findBidById_ShouldReturnBid_WhenFound() {

        // Arrange
        BidListEntity bid = new BidListEntity();
        bid.setId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // Act
        BidListEntity result = bidListService.findBidById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(bidListRepository).findById(1);
    }

    @Test
    void findBidById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        BidListEntity result = bidListService.findBidById(999);

        // Assert
        assertNull(result);
        verify(bidListRepository).findById(999);
    }

    @Test
    void deleteBid_ShouldCallRepositoryDeleteById() {

        // Act
        bidListService.deleteBid(1);

        // Assert
        verify(bidListRepository, times(1)).deleteById(1);
    }
}
