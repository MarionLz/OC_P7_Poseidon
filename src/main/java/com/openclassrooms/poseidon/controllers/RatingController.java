package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RatingEntity;
import com.openclassrooms.poseidon.repositories.RatingRepository;
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
public class RatingController {

    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    private RatingRepository ratingRepository;

    @RequestMapping("/rating/list")
    public String home(Model model, HttpServletRequest request) {

        model.addAttribute("ratings", ratingRepository.findAll());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /rating/list - OK");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {

        model.addAttribute("rating", new RatingEntity());
        logger.info("GET /rating/add - OK");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingEntity rating, BindingResult result, Model model,
                           RedirectAttributes ra) {

        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute("ratings", ratingRepository.findAll());
            ra.addFlashAttribute("successMessage", "Rating created successfully");
            logger.info("POST /rating/validate - OK");
            return "redirect:/rating/list";
        }
        model.addAttribute("errorMessage", "Validation errors found");
        logger.warn("POST /rating/validate - KO : validation errors found");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RatingEntity rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        logger.info("GET /rating/update/{} - OK", id);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingEntity rating,
                             BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            logger.warn("POST /rating/update/{} - KO : validation errors found", id);        model.addAttribute("errorMessage", "Validation errors found");
            model.addAttribute("errorMessage", "Validation errors found");
            return "rating/update";
        }
        ratingRepository.save(rating);
        model.addAttribute("ratings", ratingRepository.findAll());
        ra.addFlashAttribute("successMessage", "Rating updated successfully");
        logger.info("POST /rating/update/{} - OK", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        RatingEntity rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("ratings", ratingRepository.findAll());
        ra.addFlashAttribute("successMessage", "Rating deleted successfully");
        logger.info("GET /rating/delete/{} - OK", id);
        return "redirect:/rating/list";
    }
}