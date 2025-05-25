package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
