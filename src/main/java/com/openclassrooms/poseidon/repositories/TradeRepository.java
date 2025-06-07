package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}
