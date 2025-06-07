package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.UserEntity;
import com.openclassrooms.poseidon.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private UserEntity sampleUser() {
        return new UserEntity(1, "Username", "Password123!", "Full Name", "Role");
    }

    @Test
    void testHome() throws Exception {

        List<UserEntity> users = List.of(sampleUser());

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void testAddUserForm() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .param("username", "Username")
                        .param("password", "Password123!")
                        .param("fullname", "Full Name")
                        .param("role", "Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).saveUser(any(UserEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .param("username", "Username")
                        .param("password", "Password") // invalid password
                        .param("fullname", "Full Name")
                        .param("role", "Role"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(userService, never()).saveUser(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(userService.checkIfUserExists(1)).thenReturn(true);
        when(userService.findUserById(1)).thenReturn(sampleUser());

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(userService.checkIfUserExists(999)).thenReturn(false);

        mockMvc.perform(get("/user/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(flash().attribute("errorMessage", "User not found"));
    }

    @Test
    void testUpdateUser_Success() throws Exception {

        mockMvc.perform(post("/user/update/1")
                        .param("id", "1")
                        .param("username", "Username updated")
                        .param("password", "Password123!")
                        .param("fullname", "Full Name updated")
                        .param("role", "Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).saveUser(any(UserEntity.class));
    }

    @Test
    void testUpdateUser_Errors() throws Exception {

        mockMvc.perform(post("/user/update/1")
                        .param("id", "1")
                        .param("username", "Username")
                        .param("passwpord", "Password") // invalid password
                        .param("fullname", "Full Name")
                        .param("role", "Role"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verify(userService, never()).saveUser(any());
    }

    @Test
    void testDeleteUser_ValidId() throws Exception {

        when(userService.checkIfUserExists(1)).thenReturn(true);

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(flash().attribute("successMessage", "User deleted successfully"));

        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void testDeleteUser_InvalidId() throws Exception {

        when(userService.checkIfUserExists(999)).thenReturn(false);

        mockMvc.perform(get("/user/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(flash().attribute("errorMessage", "User not found"));

        verify(userService, never()).deleteUser(any());
    }
}
