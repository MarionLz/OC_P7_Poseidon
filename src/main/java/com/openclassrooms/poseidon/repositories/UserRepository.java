package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    /**
     * Finds a user by their email.
     *
     * @param username the username of the user to find
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<UserEntity> findByUsername(String username);
}
