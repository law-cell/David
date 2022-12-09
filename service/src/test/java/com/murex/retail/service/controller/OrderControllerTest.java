package com.murex.retail.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murex.retail.model.order.Order;
import com.murex.retail.service.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    private static Order order;

    @BeforeAll
    public static void setup() {
        order = new Order();
    }

    @Test
    void testFetchOrder() throws Exception {
        when(this.orderService.fetchOrder(order.getId())).thenReturn(order);
        String url = "/api/v1/orders/" + order.getId();
        String responseString = this.mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Order orderResponse = this.objectMapper.readValue(responseString, Order.class);
        assertEquals(order.getId(), orderResponse.getId());
    }
}