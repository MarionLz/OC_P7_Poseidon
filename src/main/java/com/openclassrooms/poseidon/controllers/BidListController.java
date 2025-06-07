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

/**
 * Controller class for managing BidList operations.
 * Handles HTTP requests related to BidList entities, such as listing, adding, updating, and deleting bids.
 */
@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    private BidListService bidListService;

    /**
     * Handles the request to display the list of bids.
     *
     * @param model the Model object to pass data to the view
     * @param request the HttpServletRequest object
     * @return the name of the view to display the list of bids
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request)
    {
        model.addAttribute("bidLists", bidListService.findAllBids());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /bidList/list - OK");
        return "bidList/list";
    }

    /**
     * Handles the request to display the form for adding a new bid.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view to display the add bid form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        model.addAttribute("bidList", new BidListEntity());
        logger.info("GET /bidList/add - OK");
        return "bidList/add";
    }

    /**
     * Handles the submission of the add bid form.
     * Validates the bid and saves it if there are no validation errors.
     *
     * @param bid the BidListEntity object to validate and save
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
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

    /**
     * Handles the request to display the form for updating an existing bid.
     *
     * @param id the ID of the bid to update
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to display the update bid form or redirect to the list view
     */
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

    /**
     * Handles the submission of the update bid form.
     * Validates the bid and updates it if there are no validation errors.
     *
     * @param id the ID of the bid to update
     * @param bid the BidListEntity object to validate and update
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidListEntity bid,
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

    /**
     * Handles the request to delete an existing bid.
     *
     * @param id the ID of the bid to delete
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to
     */
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
