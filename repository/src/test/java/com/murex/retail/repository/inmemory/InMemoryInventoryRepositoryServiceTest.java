package com.murex.retail.repository.inmemory;

import com.murex.retail.repository.inmemory.inventory.InMemoryInventoryRepository;
import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryInventoryRepositoryServiceTest {
    private final InMemoryInventoryRepository repository = new InMemoryInventoryRepository();
    private static ComputerComponent processor;
    private static ComputerComponent monitor;
    private static ComputerComponent peripheral;
    private static ComputerComponent memory;
    private static ComputerComponent externalMemory;
    private static final List<ComputerComponent> computerComponents = new ArrayList<>();
    private Category incorrectCategory;
    private Category correctCategory;

    @BeforeAll
    public static void buildComponent() {
        ComponentBuilder builder = new ComponentBuilder();
        builder.id("70d0c37e-634e-4a68-8862-0ba44f216f3b")
                .category(Category.CPU)
                .name("Intel Core i7-8809G")
                .brand("Intel")
                .price(Double.parseDouble("150"))
                .quantity(Integer.parseInt("25"));
        processor = builder.build();
        computerComponents.add(processor);

        builder.id("97222f34-2e84-48a3-a165-97962cdc8c95")
                .category(Category.MONITOR)
                .name("Asus VP229HA")
                .brand("Asus")
                .price(Double.parseDouble("130"))
                .quantity(Integer.parseInt("20"));
        monitor = builder.build();
        computerComponents.add(monitor);

        builder.id("cf6ed8eb-fe36-44a4-be81-08351cde0610")
                .category(Category.MOUSE)
                .name("Cougar 450M Gaming Mouse")
                .brand("Cougar")
                .price(Double.parseDouble("43"))
                .quantity(Integer.parseInt("10"));
        peripheral = builder.build();
        computerComponents.add(peripheral);

        builder.id("710c3100-4a24-4f9a-947a-710948dea83")
                .category(Category.MEMORY)
                .name("Kingston HyperX Impact DDR4")
                .brand("Kingston")
                .price(Double.parseDouble("95"))
                .quantity(Integer.parseInt("10"));
        memory = builder.build();
        computerComponents.add(memory);

        builder.id("75cfcec-9655-4c68-9afc-8c706685c883")
                .category(Category.STORAGE)
                .name("Kingston Digital KC1000 NVMe PCIe")
                .brand("Kingston")
                .price(Double.parseDouble("439"))
                .quantity(Integer.parseInt("12"));
        externalMemory = builder.build();
        computerComponents.add(externalMemory);
    }

    @Test
    public void testUploadAndFetchProcessor() {
        this.repository.save(processor);
        assertEquals(processor, this.repository.fetchComponent(processor.getId()).get());
    }

    @Test
    public void testUploadAndFetchPeripheral() {
        this.repository.save(peripheral);
        assertEquals(peripheral, this.repository.fetchComponent(peripheral.getId()).get());
    }

    @Test
    public void testUploadAndFetchMonitor() {
        this.repository.save(monitor);
        assertEquals(monitor, this.repository.fetchComponent(monitor.getId()).get());
    }

    @Test
    public void testUploadAndFetchMemory() {
        this.repository.save(memory);
        assertEquals(memory, this.repository.fetchComponent(memory.getId()).get());
    }

    @Test
    public void testUploadAndFetchExternalMemory() {
        this.repository.save(externalMemory);
        assertEquals(externalMemory, this.repository.fetchComponent(externalMemory.getId()).get());
    }

    @Test
    public void testUploadAndFetchProcessorByCategory() throws Exception {
        this.correctCategory = Category.CPU;
        this.incorrectCategory = Category.KEYBOARD;

        this.repository.save(processor);

        assertEquals(Optional.empty(), this.repository.fetchComponent(processor.getId(), this.incorrectCategory));
        assertEquals(processor, this.repository.fetchComponent(processor.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchPeripheralByCategory() throws Exception {
        this.correctCategory = Category.MOUSE;
        this.incorrectCategory = Category.CPU;

        this.repository.save(peripheral);

        assertEquals(Optional.empty(), this.repository.fetchComponent(peripheral.getId(), this.incorrectCategory));
        assertEquals(peripheral, this.repository.fetchComponent(peripheral.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchMonitorByCategory() throws Exception {
        this.correctCategory = Category.MONITOR;
        this.incorrectCategory = Category.CPU;

        this.repository.save(monitor);

        assertEquals(Optional.empty(), this.repository.fetchComponent(monitor.getId(), this.incorrectCategory));
        assertEquals(monitor, this.repository.fetchComponent(monitor.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchMemoryByCategory() throws Exception {
        this.correctCategory = Category.MEMORY;
        this.incorrectCategory = Category.CPU;

        this.repository.save(memory);

        assertEquals(Optional.empty(), this.repository.fetchComponent(memory.getId(), this.incorrectCategory));
        assertEquals(memory, this.repository.fetchComponent(memory.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchExternalMemoryByCategory() throws Exception {
        this.correctCategory = Category.STORAGE;
        this.incorrectCategory = Category.CPU;

        this.repository.save(externalMemory);

        assertEquals(Optional.empty(), this.repository.fetchComponent(externalMemory.getId(), this.incorrectCategory));
        assertEquals(externalMemory, this.repository.fetchComponent(externalMemory.getId(), this.correctCategory).get());
    }
}