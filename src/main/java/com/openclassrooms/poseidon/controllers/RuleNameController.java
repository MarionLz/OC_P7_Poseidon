package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger("RuleNameController");

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpServletRequest request) {

        logger.info("Fetching all rules");
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        model.addAttribute("httpServletRequest", request);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {

        logger.info("Displaying RuleName creation form");
        model.addAttribute("ruleName", new RuleNameEntity());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameEntity ruleName, BindingResult result, Model model) {

        logger.info("Validating new RuleName: {}", ruleName.getName());
        if (!result.hasErrors()) {
            ruleNameRepository.save(ruleName);
            logger.info("RuleName created successfully: {}", ruleName.getName());
            model.addAttribute("ruleNames", ruleNameRepository.findAll());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("Fetching ruleName for update with ID: {}", id);
        RuleNameEntity ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameEntity ruleName,
                             BindingResult result, Model model) {

        logger.info("Updating ruleName with ID: {}", id);
        if (result.hasErrors()) {
            logger.warn("Validation errors while updating ruleName: {}", result.getAllErrors());
            return "ruleName/update";
        }
        ruleNameRepository.save(ruleName);
        logger.info("RuleName updated successfully: {}", ruleName.getName());
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        logger.info("Deleting ruleName with ID: {}", id);
        RuleNameEntity ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        logger.info("RuleName deleted successfully: {}", ruleName.getName());
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        return "redirect:/ruleName/list";
    }
}
