package com.openclassrooms.poseidon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

/**
 * Controller class for managing login and error handling.
 * Handles HTTP requests related to user authentication and authorization errors.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    private static final Logger logger = LogManager.getLogger("LoginController");

    /**
     * Handles the request to display the login page.
     * Redirects authenticated users to the bid list page.
     *
     * @return a ModelAndView object for the login page or redirection
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, Model model, RedirectAttributes ra) {

        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                ra.addFlashAttribute("successMessage", "Admin already logged in.");
                logger.info("GET /app/login - Admin user authenticated, redirecting to /user/list");
                return new ModelAndView("redirect:/user/list");
            } else {
                ra.addFlashAttribute("successMessage", "User already logged in.");
                logger.info("GET /app/login - Regular user authenticated, redirecting to /bidList/list");
                return new ModelAndView("redirect:/bidList/list");
            }
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        logger.info("GET /app/login - OK");
        return mav;
    }

    /**
     * Handles the request to display the error page for unauthorized access.
     *
     * @param request the HttpServletRequest object
     * @return a ModelAndView object for the error page
     */
    @GetMapping("error")
    public ModelAndView error(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("httpServletRequest", request);
        mav.setViewName("error/403");
        return mav;
    }
}
