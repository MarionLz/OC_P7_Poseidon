package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.UserEntity;
import com.openclassrooms.poseidon.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {

        model.addAttribute("users", userService.findAllUsers());
        logger.info("GET /user/list - OK");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {

        model.addAttribute("user", new UserEntity());
        logger.info("GET /user/add - OK");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserEntity user, BindingResult result, Model model,
                           RedirectAttributes ra) {

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            ra.addFlashAttribute("successMessage", "User created successfully");
            logger.info("POST /user/validate - OK");
            return "redirect:/user/list";
        }
        logger.warn("POST /user/validate - KO : validation errors found");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        if (!userService.checkIfUserExists(id)) {
            ra.addFlashAttribute("errorMessage", "User not found");
            logger.warn("GET /user/update/{} - KO : User not found", id);
            return "redirect:/user/list";
        }
        model.addAttribute("user", userService.findUserById(id));
        logger.info("GET /user/update/{} - OK", id);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") UserEntity user,
                             BindingResult result, Model model, RedirectAttributes ra) {

        if (result.hasErrors()) {
            logger.warn("POST /user/update/{} - KO : validation errors found", id);
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.saveUser(user);
        model.addAttribute("users", userService.findAllUsers());
        ra.addFlashAttribute("successMessage", "User updated successfully");
        logger.info("POST /user/update/{} - OK", id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

        if (!userService.checkIfUserExists(id)) {
            ra.addFlashAttribute("errorMessage", "User not found");
            logger.warn("GET /user/delete/{} - KO : User not found", id);
            return "redirect:/user/list";
        }
        userService.deleteUser(id);
        model.addAttribute("users", userService.findAllUsers());
        ra.addFlashAttribute("successMessage", "User deleted successfully");
        logger.info("GET /user/delete/{} - OK", id);
        return "redirect:/user/list";
    }
}
