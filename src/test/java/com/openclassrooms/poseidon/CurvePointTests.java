package com.openclassrooms.poseidon;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {

		CurvePointEntity curvePoint = new CurvePointEntity();
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10d);
		curvePoint.setValue(30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
        assertEquals(10, (int) curvePoint.getCurveId());

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
        assertEquals(20, (int) curvePoint.getCurveId());

		// Find
		List<CurvePointEntity> listResult = curvePointRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePointEntity> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}
}
