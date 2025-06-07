package com.openclassrooms.poseidon.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public ModelAndView login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            logger.info("GET /app/login - User already authenticated, redirecting to /bidList/list");
            return new ModelAndView("redirect:/bidList/list");
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
