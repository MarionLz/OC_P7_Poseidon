package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
