package com.openclassrooms.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing home page navigation.
 * Handles HTTP requests for the home page and admin home redirection.
 */
@Controller
public class HomeController {

    /**
     * Handles the request to display the home page.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view to display the home page
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Handles the request to redirect to the admin home page.
     * Redirects to the bid list page for admin users.
     *
     * @param model the Model object to pass data to the view
     * @return the redirection URL for the admin home page
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }
}
