package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
