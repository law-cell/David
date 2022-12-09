package com.murex.retail.parser;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.Processor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryParserTest {
    private static final String TEST_PATH = "Test.csv";
    private static final String INCORRECT_INVENTORY_PATH = "FakeFile.csv";

    @Test
    public void testIncorrectPathThrowsException() {
        assertThrows(InventoryParserException.class, () -> InventoryParser.parseInventory(INCORRECT_INVENTORY_PATH));
    }

    @Test
    public void testInventoryParser() {
        Map<String, String> expectedFields = new HashMap<>();
        expectedFields.put("ID", "70d0c37e-634e-4a68-8862-0ba44f216f3b");
        expectedFields.put("Category", "CPU");
        expectedFields.put("Name", "Intel Core i7-8809G");
        expectedFields.put("Brand", "Intel");
        expectedFields.put("Product Line", "Core i7");
        expectedFields.put("Number Of Cores", "4");
        expectedFields.put("Processor Clock Speed", "3.10 GHz");
        expectedFields.put("Graphic Clock Speed", "1.20 GHz");
        expectedFields.put("Price", "150");
        expectedFields.put("Quantity", "25");

        Processor computerComponent = (Processor) InventoryParser.parseInventory(TEST_PATH).get(0);

        assertEquals(expectedFields.get("ID"), computerComponent.getId());
        assertEquals(Category.valueOf(expectedFields.get("Category")), computerComponent.getCategory());
        assertEquals(expectedFields.get("Name"), computerComponent.getName());
        assertEquals(expectedFields.get("Brand"), computerComponent.getBrand());
        assertEquals(expectedFields.get("Product Line"), computerComponent.getProductLine());
        assertEquals(expectedFields.get("Number Of Cores"), computerComponent.getNumberOfCores());
        assertEquals(expectedFields.get("Processor Clock Speed"), computerComponent.getProcessorClockSpeed());
        assertEquals(expectedFields.get("Graphic Clock Speed"), computerComponent.getGraphicsClockSpeed());
        assertEquals(Double.parseDouble(expectedFields.get("Price")), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(expectedFields.get("Quantity")), computerComponent.getQuantity());
    }
}
