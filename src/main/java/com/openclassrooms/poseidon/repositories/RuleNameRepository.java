package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleNameEntity, Integer> {
}
