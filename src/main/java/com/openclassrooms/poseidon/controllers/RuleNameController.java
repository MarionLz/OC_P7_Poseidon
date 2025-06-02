package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.services.RuleNameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for managing RuleName operations.
 * Handles HTTP requests related to RuleName entities, such as listing, adding, updating, and deleting rule names.
 */
@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger("RuleNameController");

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Handles the request to display the list of rule names.
     *
     * @param model the Model object to pass data to the view
     * @param request the HttpServletRequest object
     * @return the name of the view to display the list of rule names
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /ruleName/list - OK");
        return "ruleName/list";
    }

    /**
     * Handles the request to display the form for adding a new rule name.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view to display the add rule name form
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(Model model) {
        model.addAttribute("ruleName", new RuleNameEntity());
        logger.info("GET /ruleName/add - OK");
        return "ruleName/add";
    }

    /**
     * Handles the submission of the add rule name form.
     * Validates the rule name and saves it if there are no validation errors.
     *
     * @param ruleName the RuleNameEntity object to validate and save
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameEntity ruleName, BindingResult result, Model model,
                           RedirectAttributes ra) {
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
            ra.addFlashAttribute("successMessage", "Rule name created successfully");
            logger.info("POST /ruleName/validate - OK");
            return "redirect:/ruleName/list";
        }
        logger.warn("POST /ruleName/validate - KO : validation errors found");
        return "ruleName/add";
    }

    /**
     * Handles the request to display the form for updating an existing rule name.
     *
     * @param id the ID of the rule name to update
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to display the update rule name form or redirect to the list view
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!ruleNameService.checkIfRuleNameExists(id)) {
            ra.addFlashAttribute("errorMessage", "Rule name not found");
            logger.warn("GET /ruleName/update/{} - KO : Rule name not found", id);
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleName", ruleNameService.findRuleNameById(id));
        logger.info("GET /ruleName/update/{} - OK", id);
        return "ruleName/update";
    }

    /**
     * Handles the submission of the update rule name form.
     * Validates the rule name and updates it if there are no validation errors.
     *
     * @param id the ID of the rule name to update
     * @param ruleName the RuleNameEntity object to validate and update
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleName") RuleNameEntity ruleName,
                                 BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            logger.warn("POST /ruleName/update/{} - KO : validation errors found", id);
            return "ruleName/update";
        }
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        ra.addFlashAttribute("successMessage", "Rule name updated successfully");
        logger.info("POST /ruleName/update/{} - OK", id);
        return "redirect:/ruleName/list";
    }

    /**
     * Handles the request to delete an existing rule name.
     *
     * @param id the ID of the rule name to delete
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!ruleNameService.checkIfRuleNameExists(id)) {
            ra.addFlashAttribute("errorMessage", "Rule name not found");
            logger.warn("GET /ruleName/delete/{} - KO : Rule name not found", id);
            return "redirect:/ruleName/list";
        }
        ruleNameService.deleteRuleName(id);
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        ra.addFlashAttribute("successMessage", "Rule name deleted successfully");
        logger.info("GET /ruleName/delete/{} - OK", id);
        return "redirect:/ruleName/list";
    }
}