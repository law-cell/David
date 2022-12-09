package com.murex.retail.service.service;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.parser.InventoryParser;
import com.murex.retail.repository.service.InventoryRepositoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    private InventoryRepositoryService inventoryRepositoryService;
    @InjectMocks
    private InventoryService inventoryService;
    private static final String COMPONENT_NAME = "Intel Core i7-8809G";

    private static ComputerComponent component;

    @BeforeAll
    public static void setup() {
        ComponentBuilder builder = new ComponentBuilder();
        builder.id("70d0c37e-634e-4a68-8862-0ba44f216f3b")
                .category(Category.CPU)
                .name("Intel Core i7-8809G")
                .brand("Intel")
                .price(Double.parseDouble("150"))
                .quantity(Integer.parseInt("25"));
        component = builder.build();
    }

    @Test
    void testFetchAll() throws Exception {
        when(this.inventoryRepositoryService.fetchAll()).thenReturn(InventoryParser.parseInventory("Test.csv"));
        List<ComputerComponent> computerComponents = this.inventoryService.fetchAll();
        assertEquals(100, computerComponents.size());
    }

    @Test
    void testFetchComponentByName() throws Exception {
        when(this.inventoryRepositoryService.fetchComponentByName(COMPONENT_NAME)).thenReturn(component);
        assertEquals(component, this.inventoryService.fetchComponentByName(COMPONENT_NAME));
    }

    @Test
    void testCountItems() {
        when(this.inventoryRepositoryService.countItems()).thenReturn(100);
        assertEquals(100, this.inventoryService.countItems());
    }
}
