package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
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
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    void findAllRuleNames_ShouldReturnListOfRuleNames() {

        // Arrange
        RuleNameEntity ruleName1 = new RuleNameEntity();
        RuleNameEntity ruleName2 = new RuleNameEntity();
        when(ruleNameRepository.findAll()).thenReturn(List.of(ruleName1, ruleName2));

        // Act
        List<RuleNameEntity> result = ruleNameService.findAllRuleNames();

        // Assert
        assertEquals(2, result.size());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void saveRuleName_ShouldCallRepositorySave() {

        // Arrange
        RuleNameEntity ruleName = new RuleNameEntity();

        // Act
        ruleNameService.saveRuleName(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void checkIfRuleNameExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(ruleNameRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = ruleNameService.checkIfRuleNameExists(1);

        // Assert
        assertTrue(exists);
        verify(ruleNameRepository).existsById(1);
    }

    @Test
    void checkIfRuleNameExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(ruleNameRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = ruleNameService.checkIfRuleNameExists(2);

        // Assert
        assertFalse(exists);
        verify(ruleNameRepository).existsById(2);
    }

    @Test
    void findRuleNameById_ShouldReturnRuleName_WhenFound() {

        // Arrange
        RuleNameEntity ruleName = new RuleNameEntity();
        ruleName.setId(1);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        // Act
        RuleNameEntity result = ruleNameService.findRuleNameById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(ruleNameRepository).findById(1);
    }

    @Test
    void findRuleNameById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(ruleNameRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        RuleNameEntity result = ruleNameService.findRuleNameById(999);

        // Assert
        assertNull(result);
        verify(ruleNameRepository).findById(999);
    }

    @Test
    void deleteRuleName_ShouldCallRepositoryDeleteById() {

        // Act
        ruleNameService.deleteRuleName(1);

        // Assert
        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
