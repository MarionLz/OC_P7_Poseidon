package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePointEntity> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    public void saveCurvePoint(CurvePointEntity curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public boolean checkIfCurvePointExists(Integer id) {
        return curvePointRepository.existsById(id);
    }

    public CurvePointEntity findCurvePointById(Integer id) {
        return curvePointRepository.findById(id).orElse(null);
    }

    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
