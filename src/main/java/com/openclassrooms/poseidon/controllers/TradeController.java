package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger("TradeController");

    @Autowired
    private TradeRepository tradeRepository;

    @RequestMapping("/trade/list")
    public String home(Model model, HttpServletRequest request) {

        model.addAttribute("trades", tradeRepository.findAll());
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
    public String validate(@Valid TradeEntity trade, BindingResult result, Model model,
                           RedirectAttributes ra) {

        if (!result.hasErrors()) {
            tradeRepository.save(trade);
            model.addAttribute("trades", tradeRepository.findAll());
            ra.addFlashAttribute("successMessage", "Trade created successfully");
            logger.info("POST /trade/validate - OK");
            return "redirect:/trade/list";
        }
        logger.warn("POST /trade/validate - KO : validation errors found");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        TradeEntity trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        logger.info("GET /trade/update/{} - OK", id);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeEntity trade,
                             BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            logger.warn("POST /trade/update/{} - KO : validation errors found", id);
            return "trade/update";
        }
        tradeRepository.save(trade);
        model.addAttribute("trades", tradeRepository.findAll());
        ra.addFlashAttribute("successMessage", "Trade updated successfully");
        logger.info("POST /trade/update/{} - OK", id);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        TradeEntity trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trades", tradeRepository.findAll());
        ra.addFlashAttribute("successMessage", "Trade deleted successfully");
        logger.info("GET /trade/delete/{} - OK", id);
        return "redirect:/trade/list";
    }
}
