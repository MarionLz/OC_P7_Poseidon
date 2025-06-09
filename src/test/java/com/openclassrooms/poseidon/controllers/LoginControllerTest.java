package com.openclassrooms.poseidon.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;

import static org.mockito.Mockito.*;
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
    public void testLoginPage_WithErrorParameter() throws Exception {
        mockMvc.perform(get("/app/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("loginError", "Invalid username or password."));
    }

    @Test
    public void testLoginPageRedirects_WhenAdminAuthenticated() throws Exception {
        // Mock authenticated admin user
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mock(Object.class)); // Not anonymousUser
        doReturn(Collections.singletonList(adminAuthority)).when(authentication).getAuthorities();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/app/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void testLoginPageRedirects_WhenRegularUserAuthenticated() throws Exception {
        // Mock authenticated admin user
        GrantedAuthority regularUserAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mock(Object.class)); // Not anonymousUser
        doReturn(Collections.singletonList(regularUserAuthority)).when(authentication).getAuthorities();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/app/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void testErrorPage() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}
