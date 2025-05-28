package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.BidListEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidListEntity, Integer> {

}
