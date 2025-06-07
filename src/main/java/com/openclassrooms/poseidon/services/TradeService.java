package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<TradeEntity> findAllTrades() {
        return tradeRepository.findAll();
    }

    public void saveTrade(TradeEntity trade) {
        tradeRepository.save(trade);
    }

    public boolean checkIfTradeExists(Integer id) {
        return tradeRepository.existsById(id);
    }

    public TradeEntity findTradeById(Integer id) {
        return tradeRepository.findById(id).orElse(null);
    }

    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }
}
