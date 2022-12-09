package com.murex.retail.service.service;

import com.murex.retail.model.component.ComputerComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderFulfillmentServiceTest {
    @Autowired
    OrderFulfillmentService orderFulfillmentService;
    @MockBean
    InventoryService inventoryService;

    private final Map<String, Integer> componentQuantityMap = new HashMap<>();
    private static List<String> idList;
    private static final String COMPONENT_ID = "component-id";

    @BeforeAll
    public static void setup() {
        idList = new ArrayList<>() {{
            add(COMPONENT_ID);
        }};
    }

    @Test
    public void testCreateOrder() throws Exception {
        ComputerComponent computerComponent = mock(ComputerComponent.class);
        when(computerComponent.getQuantity()).thenReturn(2);
        componentQuantityMap.put(COMPONENT_ID, computerComponent.getQuantity());
        when(this.inventoryService
                .fetchComponent(COMPONENT_ID))
                .thenReturn(computerComponent);
        doNothing().when(this.inventoryService)
                .updateStock(componentQuantityMap);
        assertEquals(idList, this.orderFulfillmentService
                .createOrder(idList)
                .getIdList());
    }

    @Test
    public void testCreateOrderThrowsException() throws Exception {
        ComputerComponent computerComponent = mock(ComputerComponent.class);
        when(computerComponent.getQuantity()).thenReturn(0);
        when(this.inventoryService
                .fetchComponent(COMPONENT_ID))
                .thenReturn(computerComponent);
        try {
            this.orderFulfillmentService.createOrder(idList);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}
