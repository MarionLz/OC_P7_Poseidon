package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidListEntity;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BidListControllerTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListController bidListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    void testHome() throws Exception {

        List<BidListEntity> bidLists = Arrays.asList(new BidListEntity(), new BidListEntity());
        when(bidListRepository.findAll()).thenReturn(bidLists);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attributeExists("httpServletRequest"));

        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void testAddBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void testValidateSuccess() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(bidListRepository, times(1)).save(any(BidListEntity.class));
    }

    @Test
    void testValidateErrors() throws Exception {

        mockMvc.perform(post("/bidList/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListRepository, never()).save(any());
    }

    @Test
    void testShowUpdateForm() throws Exception {

        BidListEntity bid = new BidListEntity();
        bid.setId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void testShowUpdateFormInvalidId() throws Exception {

        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bidList/update/999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateBidSuccess() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("bidQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(bidListRepository, times(1)).save(any(BidListEntity.class));
    }

    @Test
    void testUpdateBidErrors() throws Exception {

        // Pas de paramètres = erreurs de validation
        mockMvc.perform(post("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListRepository, never()).save(any());
    }

    @Test
    void testDeleteBid() throws Exception {

        BidListEntity bid = new BidListEntity();
        bid.setId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(bidListRepository, times(1)).delete(bid);
    }

    @Test
    void testDeleteBidInvalidId() throws Exception {

        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bidList/delete/999"))
                .andExpect(status().is4xxClientError());
    }
}
