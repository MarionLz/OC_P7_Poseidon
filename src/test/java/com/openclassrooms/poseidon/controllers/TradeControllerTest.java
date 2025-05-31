package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.TradeEntity;
import com.openclassrooms.poseidon.services.TradeService;
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
public class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    private TradeEntity sampleTrade() {
        return new TradeEntity(1, "Account Test", "Type Test", 100.0);
    }

    @Test
    void testHome() throws Exception {

        List<TradeEntity> trades = List.of(sampleTrade());

        when(tradeService.findAllTrades()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(tradeService, times(1)).findAllTrades();
    }

    @Test
    void testAddTradeForm() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).saveTrade(any(TradeEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .param("account", "")  // invalid, empty account
                        .param("type", "Type Test")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, never()).saveTrade(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(tradeService.checkIfTradeExists(1)).thenReturn(true);
        when(tradeService.findTradeById(1)).thenReturn(sampleTrade());

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(tradeService.checkIfTradeExists(999)).thenReturn(false);

        mockMvc.perform(get("/trade/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(flash().attribute("errorMessage", "Trade not found"));
    }

    @Test
    void testUpdateTrade_Success() throws Exception {

        mockMvc.perform(post("/trade/update/1")
                        .param("id", "1")
                        .param("account", "Account Updated")
                        .param("type", "Type Updated")
                        .param("buyQuantity", "200.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).saveTrade(any(TradeEntity.class));
    }

    @Test
    void testUpdateTrade_Errors() throws Exception {

        mockMvc.perform(post("/trade/update/1")
                        .param("id", "1")
                        .param("account", "")  // invalid
                        .param("type", "Type Updated")
                        .param("buyQuantity", "200.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        verify(tradeService, never()).saveTrade(any());
    }

    @Test
    void testDeleteTrade_ValidId() throws Exception {

        when(tradeService.checkIfTradeExists(1)).thenReturn(true);

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(flash().attribute("successMessage", "Trade deleted successfully"));

        verify(tradeService, times(1)).deleteTrade(1);
    }

    @Test
    void testDeleteTrade_InvalidId() throws Exception {

        when(tradeService.checkIfTradeExists(999)).thenReturn(false);

        mockMvc.perform(get("/trade/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(flash().attribute("errorMessage", "Trade not found"));

        verify(tradeService, never()).deleteTrade(any());
    }
}
