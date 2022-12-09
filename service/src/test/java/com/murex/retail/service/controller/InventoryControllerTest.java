package com.murex.retail.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.model.component.Processor;
import com.murex.retail.parser.InventoryParser;
import com.murex.retail.service.service.InventoryService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InventoryService inventoryService;
    private static final String TEST_PATH = "Test.csv";

    @Test
    void testFetchAll() throws Exception {
        when(this.inventoryService.fetchAll()).thenReturn(InventoryParser.parseInventory(TEST_PATH));
        String responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ComputerComponent[] outputList = this.objectMapper.readValue(responseString, ComputerComponent[].class);
        MatcherAssert.assertThat(outputList.length, Matchers.is(100));
    }

    @Test
    void testGetByName() throws Exception {
        ComponentBuilder builder = new ComponentBuilder();
        builder.id("70d0c37e-634e-4a68-8862-0ba44f216f3b")
                .category(Category.CPU)
                .name("Intel Core i7-8809G")
                .brand("Intel")
                .price(Double.parseDouble("150"))
                .quantity(Integer.parseInt("25"));
        Processor processor = (Processor) builder.build();

        when(inventoryService.fetchComponentByName(processor.getName())).thenReturn(processor);
        String responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components/fetch/Intel Core i7-8809G")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ComputerComponent output = this.objectMapper.readValue(responseString, ComputerComponent.class);
        MatcherAssert.assertThat(output, Matchers.is(processor));
    }

    @Test
    void testGetQuantity() throws Exception {
        when(this.inventoryService.countItems()).thenReturn(100);
        String responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components/quantity")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        MatcherAssert.assertThat(Integer.parseInt(responseString), Matchers.is(100));
    }

    @Test
    void testComputeAveragePrice() throws Exception {
        when(this.inventoryService.computeAveragePrice()).thenReturn(145.83);
        String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components/operations/compute_average_price")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        double output = this.objectMapper.readValue(responseString, Double.class);
        assertEquals(145.83, output);
    }

    @Test
    void testComputeAverageCpuPrice() throws Exception {
        when(this.inventoryService.computeAverageCpuPrice()).thenReturn(92.43);
        String responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/computer_components/operations/compute_average_cpu_price")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        double output = this.objectMapper.readValue(responseString, Double.class);
        assertEquals(92.43, output);
    }
}
