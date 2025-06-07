package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleNameEntity> findAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    public void saveRuleName(RuleNameEntity ruleName) {
        ruleNameRepository.save(ruleName);
    }

    public boolean checkIfRuleNameExists(Integer id) {
        return ruleNameRepository.existsById(id);
    }

    public RuleNameEntity findRuleNameById(Integer id) {
        return ruleNameRepository.findById(id).orElse(null);
    }

    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
