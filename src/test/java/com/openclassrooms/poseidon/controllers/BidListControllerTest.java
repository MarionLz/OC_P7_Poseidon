package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import com.openclassrooms.poseidon.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @InjectMocks
    private BidListController bidListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    private BidListEntity sampleBid() {
        return new BidListEntity(1, "Account Test", "Type Test", 10.0);
    }

    @Test
    void testHome() throws Exception {

        List<BidListEntity> bids = List.of(sampleBid());

        when(bidListService.findAllBids()).thenReturn(bids);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService, times(1)).findAllBids();
    }

    @Test
    void testAddBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).saveBid(any(BidListEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")  // invalid, empty account
                        .param("type", "Type Test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).saveBid(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(bidListService.checkIfBidExists(1)).thenReturn(true);
        when(bidListService.findBidById(1)).thenReturn(sampleBid());

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(bidListService.checkIfBidExists(999)).thenReturn(false);

        mockMvc.perform(get("/bidList/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attribute("errorMessage", "Bid not found"));
    }

    @Test
    void testUpdateBid_Success() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .param("id", "1")
                        .param("account", "Account Updated")
                        .param("type", "Type Updated")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).saveBid(any(BidListEntity.class));
    }

    @Test
    void testUpdateBid_Errors() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "")  // invalid
                        .param("type", "Type Updated")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListService, never()).saveBid(any());
    }

    @Test
    void testDeleteBid_ValidId() throws Exception {

        when(bidListService.checkIfBidExists(1)).thenReturn(true);

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attribute("successMessage", "Bid deleted successfully"));

        verify(bidListService, times(1)).deleteBid(1);
    }

    @Test
    void testDeleteBid_InvalidId() throws Exception {

        when(bidListService.checkIfBidExists(999)).thenReturn(false);

        mockMvc.perform(get("/bidList/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attribute("errorMessage", "Bid not found"));

        verify(bidListService, never()).deleteBid(any());
    }
}
