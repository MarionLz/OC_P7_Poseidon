package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RatingEntity;
import com.openclassrooms.poseidon.services.RatingService;
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
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    private RatingEntity sampleRating() {

        RatingEntity rating = new RatingEntity();
        rating.setMoodysRating("MoodysTest");
        rating.setSandPRating("SandPRTest");
        rating.setFitchRating("FitchTest");
        rating.setOrderNumber(10);
        return rating;
    }

    @Test
    void testHome() throws Exception {

        List<RatingEntity> ratings = List.of(sampleRating());

        when(ratingService.findAllRatings()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService, times(1)).findAllRatings();
    }

    @Test
    void testAddRatingForm() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "MoodysTest")
                        .param("sandPRating", "SandPRTest")
                        .param("fitchRating", "FitchTest")
                        .param("orderNumber", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).saveRating(any(RatingEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "") // invalid
                        .param("sandPRating", "SandPRTest")
                        .param("fitchRating", "FitchTest")
                        .param("orderNumber", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, never()).saveRating(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(ratingService.checkIfRatingExists(1)).thenReturn(true);
        when(ratingService.findRatingById(1)).thenReturn(sampleRating());

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(ratingService.checkIfRatingExists(999)).thenReturn(false);

        mockMvc.perform(get("/rating/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(flash().attribute("errorMessage", "Rating not found"));
    }

    @Test
    void testUpdateRating_Success() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                        .param("id", "1")
                        .param("moodysRating", "MoodysTest1")
                        .param("sandPRating", "SandPRTest1")
                        .param("fitchRating", "FitchTest1")
                        .param("orderNumber", "101"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).saveRating(any(RatingEntity.class));
    }

    @Test
    void testUpdateRating_Errors() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                        .param("id", "1")
                        .param("moodysRating", "") // invalid
                        .param("sandPRating", "SandPRTest1")
                        .param("fitchRating", "FitchTest1")
                        .param("orderNumber", "101"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verify(ratingService, never()).saveRating(any());
    }

    @Test
    void testDeleteRating_ValidId() throws Exception {

        when(ratingService.checkIfRatingExists(1)).thenReturn(true);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(flash().attribute("successMessage", "Rating deleted successfully"));

        verify(ratingService, times(1)).deleteRating(1);
    }

    @Test
    void testDeleteRating_InvalidId() throws Exception {

        when(ratingService.checkIfRatingExists(999)).thenReturn(false);

        mockMvc.perform(get("/rating/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(flash().attribute("errorMessage", "Rating not found"));

        verify(ratingService, never()).deleteRating(any());
    }
}
