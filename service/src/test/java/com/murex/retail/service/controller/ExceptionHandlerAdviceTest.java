package com.murex.retail.service.controller;

import com.murex.retail.service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerAdviceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    InventoryService inventoryService;

    @Test
    void testExceptionHandlerAdvice() throws Exception {
        String componentName = "FAKE-NAME";
        String exceptionMessage = "Unable to fetch component with name: " + componentName;
        when(this.inventoryService.fetchComponentByName(componentName)).thenThrow(new Exception(exceptionMessage));
        String responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components/fetch/" + componentName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(exceptionMessage, responseString);
    }
}
