package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("app")
public class LoginController {

    private static final Logger logger = LogManager.getLogger("LoginController");

//    @Autowired
//    private UserRepository userRepository;

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

//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        logger.info("GET /app/secure/article-details - OK");
//        return mav;
//    }

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
