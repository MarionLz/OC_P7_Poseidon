package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.repositories.BidListRepository;
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
    private BidListRepository bidListRepository;

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request)
    {
        model.addAttribute("bidLists", bidListRepository.findAll());
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
    public String validate(@Valid @ModelAttribute("bidList") BidListEntity bidList, BindingResult result, Model model,
                           RedirectAttributes ra) {

        if (!result.hasErrors()) {
            bidListRepository.save(bidList);
            model.addAttribute("bidLists", bidListRepository.findAll());
            ra.addFlashAttribute("successMessage", "Bid list created successfully");
            logger.info("POST /bidList/validate - OK");
            return "redirect:/bidList/list";
        }
        logger.warn("POST /bidList/validate - KO : validation errors found");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidListEntity bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid list Id:" + id));
        model.addAttribute("bidList", bidList);
        logger.info("GET /bidList/update/{} - OK", id);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListEntity bidList,
                             BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            logger.warn("POST /bidList/update/{} - KO : validation errors found", id);
            return "bidList/update";
        }
        bidListRepository.save(bidList);
        model.addAttribute("bidLists", bidListRepository.findAll());
        ra.addFlashAttribute("successMessage", "Bid list updated successfully");
        logger.info("POST /bidList/update/{} - OK", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        BidListEntity bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid list Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidLists", bidListRepository.findAll());
        ra.addFlashAttribute("successMessage", "Bid list deleted successfully");
        logger.info("GET /bidList/delete/{} - OK", id);
        return "redirect:/bidList/list";
    }
}
