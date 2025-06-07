package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.services.TradeService;
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
 * Controller class for managing Trade operations.
 * Handles HTTP requests related to Trade entities, such as listing, adding, updating, and deleting trades.
 */
@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger("TradeController");

    @Autowired
    private TradeService tradeService;

    /**
     * Handles the request to display the list of trades.
     *
     * @param model the Model object to pass data to the view
     * @param request the HttpServletRequest object
     * @return the name of the view to display the list of trades
     */
    @RequestMapping("/trade/list")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("trades", tradeService.findAllTrades());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /trade/list - OK");
        return "trade/list";
    }

    /**
     * Handles the request to display the form for adding a new trade.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view to display the add trade form
     */
    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new TradeEntity());
        logger.info("GET /trade/add - OK");
        return "trade/add";
    }

    /**
     * Handles the submission of the add trade form.
     * Validates the trade and saves it if there are no validation errors.
     *
     * @param trade the TradeEntity object to validate and save
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") TradeEntity trade, BindingResult result, Model model,
                           RedirectAttributes ra) {
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.findAllTrades());
            ra.addFlashAttribute("successMessage", "Trade created successfully");
            logger.info("POST /trade/validate - OK");
            return "redirect:/trade/list";
        }
        logger.warn("POST /trade/validate - KO : validation errors found");
        return "trade/add";
    }

    /**
     * Handles the request to display the form for updating an existing trade.
     *
     * @param id the ID of the trade to update
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to display the update trade form or redirect to the list view
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!tradeService.checkIfTradeExists(id)) {
            ra.addFlashAttribute("errorMessage", "Trade not found");
            logger.warn("GET /trade/update/{} - KO : Trade not found", id);
            return "redirect:/trade/list";
        }
        model.addAttribute("trade", tradeService.findTradeById(id));
        logger.info("GET /trade/update/{} - OK", id);
        return "trade/update";
    }

    /**
     * Handles the submission of the update trade form.
     * Validates the trade and updates it if there are no validation errors.
     *
     * @param id the ID of the trade to update
     * @param trade the TradeEntity object to validate and update
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute("trade") TradeEntity trade,
                             BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            logger.warn("POST /trade/update/{} - KO : validation errors found", id);
            return "trade/update";
        }
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.findAllTrades());
        ra.addFlashAttribute("successMessage", "Trade updated successfully");
        logger.info("POST /trade/update/{} - OK", id);
        return "redirect:/trade/list";
    }

    /**
     * Handles the request to delete an existing trade.
     *
     * @param id the ID of the trade to delete
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!tradeService.checkIfTradeExists(id)) {
            ra.addFlashAttribute("errorMessage", "Trade not found");
            logger.warn("GET /trade/delete/{} - KO : Trade not found", id);
            return "redirect:/trade/list";
        }
        tradeService.deleteTrade(id);
        model.addAttribute("trades", tradeService.findAllTrades());
        ra.addFlashAttribute("successMessage", "Trade deleted successfully");
        logger.info("GET /trade/delete/{} - OK", id);
        return "redirect:/trade/list";
    }
}