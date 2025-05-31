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

@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger("TradeController");

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model, HttpServletRequest request) {

        model.addAttribute("trades", tradeService.findAllTrades());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /trade/list - OK");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {

        model.addAttribute("trade", new TradeEntity());
        logger.info("GET /trade/add - OK");
        return "trade/add";
    }

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
