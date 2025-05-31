//package com.openclassrooms.poseidon.services;
//
//import com.openclassrooms.poseidon.domain.UserEntity;
//import com.openclassrooms.poseidon.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class LoginServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private LoginService loginService;
//
//    private UserEntity userEntity;
//
//    @BeforeEach
//    void setup() {
//
//        userEntity = new UserEntity();
//        userEntity.setUsername("username123");
//        userEntity.setPassword("password123");
//        userEntity.setRole("USER");
//    }
//}
