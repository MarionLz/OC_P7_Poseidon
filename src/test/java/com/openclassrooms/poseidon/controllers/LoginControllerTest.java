package com.openclassrooms.poseidon.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    private LoginController loginController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        loginController = new LoginController();

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver)
                .build();    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testErrorPage() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}
