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

@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request)
    {
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        model.addAttribute("httpServletRequest", request);
        logger.info("GET /curvePoint/list - OK");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {

        model.addAttribute("curvePoint", new CurvePointEntity());
        logger.info("GET /curvePoint/add - OK");
        return "curvePoint/add";
    }

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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        if (!curvePointService.checkIfCurvePointExists(id)) {
            ra.addFlashAttribute("errorMessage", "Curve point not found");
            logger.warn("GET /curvePoint/update/{} - KO : Curve point not found", id);
            return "redirect:/curvePoint/list";
        }        model.addAttribute("curvePoint", curvePointService.findCurvePointById(id));
        logger.info("GET /curvePoint/update/{} - OK", id);
        return "curvePoint/update";
    }

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
