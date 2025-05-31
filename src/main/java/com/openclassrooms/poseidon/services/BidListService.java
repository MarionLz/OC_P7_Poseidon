package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidListEntity> findAllBids() {
        return bidListRepository.findAll();
    }

    public void saveBid(BidListEntity bid) {
        bidListRepository.save(bid);
    }

    public boolean checkIfBidExists(Integer id) {
        return bidListRepository.existsById(id);
    }

    public BidListEntity findBidById(Integer id) {
        return bidListRepository.findById(id).orElse(null);
    }

    public void deleteBid(Integer id) {
        bidListRepository.deleteById(id);
    }
}
