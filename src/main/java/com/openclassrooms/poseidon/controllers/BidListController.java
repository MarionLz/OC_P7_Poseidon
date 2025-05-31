package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.services.BidListService;
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
public class BidListController {

    private static final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request)
    {
        model.addAttribute("bidLists", bidListService.findAllBids());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /bidList/list - OK");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        model.addAttribute("bidList", new BidListEntity());
        logger.info("GET /bidList/add - OK");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidListEntity bid, BindingResult result, Model model,
                           RedirectAttributes ra) {

        if (!result.hasErrors()) {
            bidListService.saveBid(bid);
            model.addAttribute("bidLists", bidListService.findAllBids());
            ra.addFlashAttribute("successMessage", "Bid created successfully");
            logger.info("POST /bidList/validate - OK");
            return "redirect:/bidList/list";
        }
        logger.warn("POST /bidList/validate - KO : validation errors found");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        if (!bidListService.checkIfBidExists(id)) {
            ra.addFlashAttribute("errorMessage", "Bid not found");
            logger.warn("GET /bidList/update/{} - KO : Bid not found", id);
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", bidListService.findBidById(id));
        logger.info("GET /bidList/update/{} - OK", id);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListEntity bid,
                             BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            logger.warn("POST /bidList/update/{} - KO : validation errors found", id);
            return "bidList/update";
        }
        bidListService.saveBid(bid);
        model.addAttribute("bidLists", bidListService.findAllBids());
        ra.addFlashAttribute("successMessage", "Bid updated successfully");
        logger.info("POST /bidList/update/{} - OK", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        if (!bidListService.checkIfBidExists(id)) {
            ra.addFlashAttribute("errorMessage", "Bid not found");
            logger.warn("GET /bidList/delete/{} - KO : Bid not found", id);
            return "redirect:/bidList/list";
        }
        bidListService.deleteBid(id);
        model.addAttribute("bidLists", bidListService.findAllBids());
        ra.addFlashAttribute("successMessage", "Bid deleted successfully");
        logger.info("GET /bidList/delete/{} - OK", id);
        return "redirect:/bidList/list";
    }
}
