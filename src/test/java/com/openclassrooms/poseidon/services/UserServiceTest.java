package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.UserEntity;
import com.openclassrooms.poseidon.repositories.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllUsers_ShouldReturnListOfUsers() {

        // Arrange
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserEntity> result = userService.findAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser_ShouldCallRepositorySave() {

        // Arrange
        UserEntity user = new UserEntity();

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void checkIfUserExists_ShouldReturnTrue_WhenExists() {

        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = userService.checkIfUserExists(1);

        // Assert
        assertTrue(exists);
        verify(userRepository).existsById(1);
    }

    @Test
    void checkIfUserExists_ShouldReturnFalse_WhenNotExists() {

        // Arrange
        when(userRepository.existsById(2)).thenReturn(false);

        // Act
        boolean exists = userService.checkIfUserExists(2);

        // Assert
        assertFalse(exists);
        verify(userRepository).existsById(2);
    }

    @Test
    void findUserById_ShouldReturnUser_WhenFound() {

        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.findUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userRepository).findById(1);
    }

    @Test
    void findUserById_ShouldReturnNull_WhenNotFound() {

        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        UserEntity result = userService.findUserById(999);

        // Assert
        assertNull(result);
        verify(userRepository).findById(999);
    }

    @Test
    void deleteUser_ShouldCallRepositoryDeleteById() {

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }
}
