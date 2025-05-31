package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleNameEntity;
import com.openclassrooms.poseidon.services.RuleNameService;
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
public class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @InjectMocks
    private RuleNameController ruleNameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();
    }

    private RuleNameEntity sampleRuleName() {
        return new RuleNameEntity(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }

    @Test
    void testHome() throws Exception {

        List<RuleNameEntity> ruleNames = List.of(sampleRuleName());

        when(ruleNameService.findAllRuleNames()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService, times(1)).findAllRuleNames();
    }

    @Test
    void testAddRuleNameForm() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void testValidate_Success() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).saveRuleName(any(RuleNameEntity.class));
    }

    @Test
    void testValidate_Errors() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "") // invalid, empty name
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(ruleNameService, never()).saveRuleName(any());
    }

    @Test
    void testShowUpdateForm_ValidId() throws Exception {

        when(ruleNameService.checkIfRuleNameExists(1)).thenReturn(true);
        when(ruleNameService.findRuleNameById(1)).thenReturn(sampleRuleName());

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void testShowUpdateForm_InvalidId() throws Exception {

        when(ruleNameService.checkIfRuleNameExists(999)).thenReturn(false);

        mockMvc.perform(get("/ruleName/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(flash().attribute("errorMessage", "Rule name not found"));
    }

    @Test
    void testUpdateRuleName_Success() throws Exception {

        mockMvc.perform(post("/ruleName/update/1")
                        .param("id", "1")
                        .param("name", "Rule Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).saveRuleName(any(RuleNameEntity.class));
    }

    @Test
    void testUpdateRuleName_Errors() throws Exception {

        mockMvc.perform(post("/ruleName/update/1")
                        .param("id", "1")
                        .param("name", "") // invalid, empty name
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL")
                        .param("sqlPart", "SQL Part"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        verify(ruleNameService, never()).saveRuleName(any());
    }

    @Test
    void testDeleteRuleName_ValidId() throws Exception {

        when(ruleNameService.checkIfRuleNameExists(1)).thenReturn(true);

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(flash().attribute("successMessage", "Rule name deleted successfully"));

        verify(ruleNameService, times(1)).deleteRuleName(1);
    }

    @Test
    void testDeleteRuleName_InvalidId() throws Exception {

        when(ruleNameService.checkIfRuleNameExists(999)).thenReturn(false);

        mockMvc.perform(get("/ruleName/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(flash().attribute("errorMessage", "Rule name not found"));

        verify(ruleNameService, never()).deleteRuleName(any());
    }
}
