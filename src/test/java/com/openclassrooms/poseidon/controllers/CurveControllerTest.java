package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.CurvePointEntity;
import com.openclassrooms.poseidon.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CurveControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @InjectMocks
    private CurveController curveController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
    }

    private CurvePointEntity sampleCurvePoint() {

        CurvePointEntity curvePoint = new CurvePointEntity();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        return curvePoint;
    }

    @Test
    void testHome() throws Exception {

        List<CurvePointEntity> curvePoints = List.of(sampleCurvePoint());

        when(curvePointService.findAllCurvePoints()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(curvePointService, times(1)).findAllCurvePoints();
    }

    @Test
    void testAddCurvePointForm() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "10d")
                        .param("value", "30d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePointEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")  // invalid, empty account
                        .param("term", "10d")
                        .param("value", "30d"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, never()).saveCurvePoint(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(curvePointService.checkIfCurvePointExists(1)).thenReturn(true);
        when(curvePointService.findCurvePointById(1)).thenReturn(sampleCurvePoint());

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(curvePointService.checkIfCurvePointExists(999)).thenReturn(false);

        mockMvc.perform(get("/curvePoint/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(flash().attribute("errorMessage", "Curve point not found"));
    }

    @Test
    void testUpdateCurvePoint_Success() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("id", "1")
                        .param("curveId", "20")
                        .param("term", "50d")
                        .param("value", "70d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePointEntity.class));
    }

    @Test
    void testUpdateCurvePoint_Errors() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "") // invalid
                        .param("term", "50d")
                        .param("value", "70d"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        verify(curvePointService, never()).saveCurvePoint(any());
    }

    @Test
    void testDeleteCurvePoint_ValidId() throws Exception {

        when(curvePointService.checkIfCurvePointExists(1)).thenReturn(true);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(flash().attribute("successMessage", "Curve point deleted successfully"));

        verify(curvePointService, times(1)).deleteCurvePoint(1);
    }

    @Test
    void testDeleteCurvePoint_InvalidId() throws Exception {

        when(curvePointService.checkIfCurvePointExists(999)).thenReturn(false);

        mockMvc.perform(get("/curvePoint/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(flash().attribute("errorMessage", "Curve point not found"));

        verify(curvePointService, never()).deleteCurvePoint(any());
    }
}
