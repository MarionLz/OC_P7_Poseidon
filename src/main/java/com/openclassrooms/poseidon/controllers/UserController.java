package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.UserEntity;
import com.openclassrooms.poseidon.repositories.UserRepository;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {

        logger.info("Fetching all users");
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {

        logger.info("Displaying user creation form");
        model.addAttribute("user", new UserEntity());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserEntity user, BindingResult result, Model model) {

        logger.info("Validating new user: {}", user.getUsername());
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            logger.info("User created successfully: {}", user.getUsername());
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("Fetching user for update with ID: {}", id);
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserEntity user,
                             BindingResult result, Model model) {

        logger.info("Updating user with ID: {}", id);
        if (result.hasErrors()) {
            logger.warn("Validation errors while updating user: {}", result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        logger.info("User updated successfully: {}", user.getUsername());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        logger.info("Deleting user with ID: {}", id);
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        logger.info("User deleted successfully: {}", user.getUsername());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
