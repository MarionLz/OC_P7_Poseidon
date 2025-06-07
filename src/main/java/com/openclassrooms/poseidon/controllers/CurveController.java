package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import com.openclassrooms.poseidon.services.CurvePointService;
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

import java.sql.Timestamp;

/**
 * Controller class for managing CurvePoint operations.
 * Handles HTTP requests related to CurvePoint entities, such as listing, adding, updating, and deleting curve points.
 */
@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Handles the request to display the list of curve points.
     *
     * @param model the Model object to pass data to the view
     * @param request the HttpServletRequest object
     * @return the name of the view to display the list of curve points
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /curvePoint/list - OK");
        return "curvePoint/list";
    }

    /**
     * Handles the request to display the form for adding a new curve point.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view to display the add curve point form
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointEntity());
        logger.info("GET /curvePoint/add - OK");
        return "curvePoint/add";
    }

    /**
     * Handles the submission of the add curve point form.
     * Validates the curve point and saves it if there are no validation errors.
     *
     * @param curvePoint the CurvePointEntity object to validate and save
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointEntity curvePoint, BindingResult result, Model model,
                           RedirectAttributes ra) {
        if (!result.hasErrors()) {
            curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
            ra.addFlashAttribute("successMessage", "Curve point created successfully");
            logger.info("POST /curvePoint/validate - OK");
            return "redirect:/curvePoint/list";
        }
        logger.warn("POST /curvePoint/validate - KO : validation errors found");
        return "curvePoint/add";
    }

    /**
     * Handles the request to display the form for updating an existing curve point.
     *
     * @param id the ID of the curve point to update
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to display the update curve point form or redirect to the list view
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!curvePointService.checkIfCurvePointExists(id)) {
            ra.addFlashAttribute("errorMessage", "Curve point not found");
            logger.warn("GET /curvePoint/update/{} - KO : Curve point not found", id);
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePointService.findCurvePointById(id));
        logger.info("GET /curvePoint/update/{} - OK", id);
        return "curvePoint/update";
    }

    /**
     * Handles the submission of the update curve point form.
     * Validates the curve point and updates it if there are no validation errors.
     *
     * @param id the ID of the curve point to update
     * @param curvePoint the CurvePointEntity object to validate and update
     * @param result the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to or display
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurvePointEntity curvePoint,
                                   BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            logger.warn("POST /curvePoint/update/{} - KO : validation errors found", id);
            return "curvePoint/update";
        }
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        ra.addFlashAttribute("successMessage", "Curve point updated successfully");
        logger.info("POST /curvePoint/update/{} - OK", id);
        return "redirect:/curvePoint/list";
    }

    /**
     * Handles the request to delete an existing curve point.
     *
     * @param id the ID of the curve point to delete
     * @param model the Model object to pass data to the view
     * @param ra the RedirectAttributes object to pass flash attributes
     * @return the name of the view to redirect to
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        if (!curvePointService.checkIfCurvePointExists(id)) {
            ra.addFlashAttribute("errorMessage", "Curve point not found");
            logger.warn("GET /curvePoint/delete/{} - KO : Curve point not found", id);
            return "redirect:/curvePoint/list";
        }
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        ra.addFlashAttribute("successMessage", "Curve point deleted successfully");
        logger.info("GET /curvePoint/delete/{} - OK", id);
        return "redirect:/curvePoint/list";
    }
}