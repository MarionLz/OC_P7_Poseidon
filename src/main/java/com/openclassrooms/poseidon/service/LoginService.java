package com.openclassrooms.poseidon.service;

import com.openclassrooms.poseidon.domain.UserEntity;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user authentication and loading user details.
 * Implements the UserDetailsService interface to integrate with Spring Security.
 */
@Service
public class LoginService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger("LoginService");

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their email address.
     * Retrieves the user from the database and converts it into a UserDetails object for Spring Security.
     *
     * @param username the username of the user to load
     * @return a UserDetails object containing the user's information
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Attempting to load user with username: {}", username);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("User with username '{}' successfully loaded.", username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
