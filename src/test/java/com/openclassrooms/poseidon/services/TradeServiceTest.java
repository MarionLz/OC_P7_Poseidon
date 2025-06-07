package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.repositories.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void findAllTrades_ShouldReturnListOfTrades() {

        // Arrange
        TradeEntity trade1 = new TradeEntity();
        TradeEntity trade2 = new TradeEntity();
        when(tradeRepository.findAll()).thenReturn(List.of(trade1, trade2));

        // Act
        List<TradeEntity> result = tradeService.findAllTrades();

        // Assert
        assertEquals(2, result.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void saveTrade_ShouldCallRepositorySave() {

        // Arrange
        TradeEntity trade = new TradeEntity();
        trade.setAccount("TestAccount");

        // Act
        tradeService.saveTrade(trade);

        // Assert
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void checkIfTradeExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(tradeRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = tradeService.checkIfTradeExists(1);

        // Assert
        assertTrue(exists);
        verify(tradeRepository).existsById(1);
    }

    @Test
    void checkIfTradeExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(tradeRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = tradeService.checkIfTradeExists(2);

        // Assert
        assertFalse(exists);
        verify(tradeRepository).existsById(2);
    }

    @Test
    void findTradeById_ShouldReturnTrade_WhenFound() {

        // Arrange
        TradeEntity trade = new TradeEntity();
        trade.setId(1);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // Act
        TradeEntity result = tradeService.findTradeById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(tradeRepository).findById(1);
    }

    @Test
    void findTradeById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(tradeRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        TradeEntity result = tradeService.findTradeById(999);

        // Assert
        assertNull(result);
        verify(tradeRepository).findById(999);
    }

    @Test
    void deleteTrade_ShouldCallRepositoryDeleteById() {

        // Act
        tradeService.deleteTrade(1);

        // Assert
        verify(tradeRepository, times(1)).deleteById(1);
    }
}
