package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
