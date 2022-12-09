package com.murex.retail.model;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ExternalMemory;
import com.murex.retail.model.component.Memory;
import com.murex.retail.model.component.Monitor;
import com.murex.retail.model.component.Peripheral;
import com.murex.retail.model.component.Processor;
import com.murex.retail.model.factory.ComponentFactory;
import org.junit.jupiter.api.Test;

import static com.murex.retail.model.component.ComponentProperty.BRAND;
import static com.murex.retail.model.component.ComponentProperty.CATEGORY;
import static com.murex.retail.model.component.ComponentProperty.COLOR;
import static com.murex.retail.model.component.ComponentProperty.DIMENSION;
import static com.murex.retail.model.component.ComponentProperty.GRAPHIC_CLOCK_SPEED;
import static com.murex.retail.model.component.ComponentProperty.ID;
import static com.murex.retail.model.component.ComponentProperty.INTERFACE;
import static com.murex.retail.model.component.ComponentProperty.NAME;
import static com.murex.retail.model.component.ComponentProperty.NUMBER_OF_CORES;
import static com.murex.retail.model.component.ComponentProperty.PRICE;
import static com.murex.retail.model.component.ComponentProperty.PROCESSOR_CLOCK_SPEED;
import static com.murex.retail.model.component.ComponentProperty.QUANTITY;
import static com.murex.retail.model.component.ComponentProperty.RESOLUTION;
import static com.murex.retail.model.component.ComponentProperty.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComponentFactoryTest {

    @Test
    public void testInvalidCategoryThrowsException() {
        String[] invalidCategory = new String[15];
        invalidCategory[CATEGORY.getIndex()] = "INVALID_CATEGORY";

        assertThrows(IllegalArgumentException.class, () -> ComponentFactory.getComponent(invalidCategory));
    }

    @Test
    public void testFactoryGetProcessor() {
        String[] validProcessor = new String[]{"70d0c37e-634e-4a68-8862-0ba44f216f3b",
                "CPU",
                "Intel Core i7-8809G",
                "Intel",
                "Core i7",
                "4",
                "3.10 GHz",
                "1.20 GHz",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "150",
                "25"
        };

        Processor computerComponent = (Processor) ComponentFactory.getComponent(validProcessor);
        assertEquals(validProcessor[ID.getIndex()], computerComponent.getId());
        assertEquals(Category.valueOf(validProcessor[CATEGORY.getIndex()].toUpperCase()), computerComponent.getCategory());
        assertEquals(validProcessor[NAME.getIndex()], computerComponent.getName());
        assertEquals(validProcessor[BRAND.getIndex()], computerComponent.getBrand());
        assertEquals(validProcessor[NUMBER_OF_CORES.getIndex()], computerComponent.getNumberOfCores());
        assertEquals(validProcessor[PROCESSOR_CLOCK_SPEED.getIndex()], computerComponent.getProcessorClockSpeed());
        assertEquals(validProcessor[GRAPHIC_CLOCK_SPEED.getIndex()], computerComponent.getGraphicsClockSpeed());
        assertEquals(Double.parseDouble(validProcessor[PRICE.getIndex()]), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(validProcessor[QUANTITY.getIndex()]), computerComponent.getQuantity());
    }

    @Test
    public void testFactoryGetPeripheral() {
        String[] validPeripheral = new String[]{"3edbfa43-12e6-404b-99e7-48226c9a4588",
                "Keyboard",
                "Natural Ergonomic Keyboard 4000",
                "Microsoft",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "19.8W x 3.3L x 10.3D",
                "N/A",
                "Black",
                "N/A",
                "N/A",
                "50",
                "10"
        };

        Peripheral computerComponent = (Peripheral) ComponentFactory.getComponent(validPeripheral);
        assertEquals(validPeripheral[ID.getIndex()], computerComponent.getId());
        assertEquals(Category.valueOf(validPeripheral[CATEGORY.getIndex()].toUpperCase()), computerComponent.getCategory());
        assertEquals(validPeripheral[NAME.getIndex()], computerComponent.getName());
        assertEquals(validPeripheral[BRAND.getIndex()], computerComponent.getBrand());
        assertEquals(validPeripheral[COLOR.getIndex()], computerComponent.getColor());
        assertEquals(validPeripheral[DIMENSION.getIndex()], computerComponent.getDimension());
        assertEquals(Double.parseDouble(validPeripheral[PRICE.getIndex()]), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(validPeripheral[QUANTITY.getIndex()]), computerComponent.getQuantity());
    }

    @Test
    public void testFactoryGetMonitor() {
        String[] validMonitor = new String[]{"ed0062b3-29f3-4a0c-b5ec-0f97aeaa2d99",
                "Monitor",
                "Samsung C27H580FDM Curved",
                "Samsung",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "27",
                "1920 x 1080",
                "Black",
                "N/A",
                "N/A",
                "292",
                "12"
        };

        Monitor computerComponent = (Monitor) ComponentFactory.getComponent(validMonitor);
        assertEquals(validMonitor[ID.getIndex()], computerComponent.getId());
        assertEquals(Category.valueOf(validMonitor[CATEGORY.getIndex()].toUpperCase()), computerComponent.getCategory());
        assertEquals(validMonitor[NAME.getIndex()], computerComponent.getName());
        assertEquals(validMonitor[BRAND.getIndex()], computerComponent.getBrand());
        assertEquals(validMonitor[COLOR.getIndex()], computerComponent.getColor());
        assertEquals(validMonitor[DIMENSION.getIndex()], computerComponent.getDimension());
        assertEquals(validMonitor[RESOLUTION.getIndex()], computerComponent.getResolution());
        assertEquals(Double.parseDouble(validMonitor[PRICE.getIndex()]), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(validMonitor[QUANTITY.getIndex()]), computerComponent.getQuantity());
    }

    @Test
    public void testFactoryGetMemory() {
        String[] validMemory = new String[]{"abd86688-2ed5-4a16-af25-e7e2118a1af0",
                "Memory",
                "Patriot DDR4-3400",
                "Patriot",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "DDR4",
                "16GB",
                "229",
                "5"
        };

        Memory computerComponent = (Memory) ComponentFactory.getComponent(validMemory);
        assertEquals(validMemory[ID.getIndex()], computerComponent.getId());
        assertEquals(Category.valueOf(validMemory[CATEGORY.getIndex()].toUpperCase()), computerComponent.getCategory());
        assertEquals(validMemory[NAME.getIndex()], computerComponent.getName());
        assertEquals(validMemory[BRAND.getIndex()], computerComponent.getBrand());
        assertEquals(validMemory[INTERFACE.getIndex()], computerComponent.getInterface());
        assertEquals(validMemory[SIZE.getIndex()], computerComponent.getSize());
        assertEquals(Double.parseDouble(validMemory[PRICE.getIndex()]), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(validMemory[QUANTITY.getIndex()]), computerComponent.getQuantity());
    }

    @Test
    public void testFactoryGetExternalMemory() {
        String[] validExternalMemory = new String[]{"375cfcec-9655-4c68-9afc-8c706685c883",
                "Storage",
                "Kingston Digital KC1000 NVMe PCIe",
                "Kingston",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "2.5",
                "N/A",
                "N/A",
                "SSD",
                "960GB",
                "439",
                "12"
        };

        ExternalMemory computerComponent = (ExternalMemory) ComponentFactory.getComponent(validExternalMemory);
        assertEquals(validExternalMemory[ID.getIndex()], computerComponent.getId());
        assertEquals(Category.valueOf(validExternalMemory[CATEGORY.getIndex()].toUpperCase()), computerComponent.getCategory());
        assertEquals(validExternalMemory[NAME.getIndex()], computerComponent.getName());
        assertEquals(validExternalMemory[BRAND.getIndex()], computerComponent.getBrand());
        assertEquals(validExternalMemory[INTERFACE.getIndex()], computerComponent.getInterface());
        assertEquals(validExternalMemory[SIZE.getIndex()], computerComponent.getSize());
        assertEquals(validExternalMemory[DIMENSION.getIndex()], computerComponent.getDimension());
        assertEquals(Double.parseDouble(validExternalMemory[PRICE.getIndex()]), computerComponent.getPrice(), 0.0);
        assertEquals(Integer.parseInt(validExternalMemory[QUANTITY.getIndex()]), computerComponent.getQuantity());
    }
}
