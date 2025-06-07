package com.openclassrooms.poseidon;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {

		TradeEntity trade = new TradeEntity("Trade Account", "Type", 100.0);

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getId());
        assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
        assertEquals("Trade Account Update", trade.getAccount());

		// Find
		List<TradeEntity> listResult = tradeRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<TradeEntity> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
