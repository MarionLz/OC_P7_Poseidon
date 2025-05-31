package com.openclassrooms.poseidon;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {

		RuleNameEntity rule = new RuleNameEntity("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
        assertEquals("Rule Name", rule.getName());

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
        assertEquals("Rule Name Update", rule.getName());

		// Find
		List<RuleNameEntity> listResult = ruleNameRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleNameEntity> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}
}
