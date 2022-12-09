package com.murex.retail.repository.hibernate;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.hibernate.inventory.HibernateInventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateInventoryRepositoryTest {
    private final HibernateDatabaseTerminator terminator = new HibernateDatabaseTerminator();
    private final HibernateInventoryRepository repository = new HibernateInventoryRepository();
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

    @AfterEach
    public void breakDown() {
        this.terminator.terminate(ComponentTable.values());
    }

    @Test
    public void testUploadAndFetchProcessor() throws Exception {
        this.repository.save(processor);
        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(processor.getId());
        assertTrue(computerComponent.isPresent());
        assertEquals(processor, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchPeripheral() throws Exception {
        this.repository.save(peripheral);
        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(peripheral.getId());
        assertTrue(computerComponent.isPresent());
        assertEquals(peripheral, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchMonitor() throws Exception {
        this.repository.save(monitor);
        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(monitor.getId());
        assertTrue(computerComponent.isPresent());
        assertEquals(monitor, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchMemory() throws Exception {
        this.repository.save(memory);
        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(memory.getId());
        assertTrue(computerComponent.isPresent());
        assertEquals(memory, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchExternalMemory() throws Exception {
        this.repository.save(externalMemory);
        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(externalMemory.getId());
        assertTrue(computerComponent.isPresent());
        assertEquals(externalMemory, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchProcessorByCategory() throws Exception {
        this.correctCategory = Category.CPU;
        this.incorrectCategory = Category.KEYBOARD;

        this.repository.save(processor);

        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(processor.getId(), incorrectCategory);
        assertTrue(computerComponent.isEmpty());
        computerComponent = this.repository.fetchComponent(processor.getId(), correctCategory);
        assertTrue(computerComponent.isPresent());
        assertEquals(processor, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchPeripheralByCategory() throws Exception {
        this.correctCategory = Category.MOUSE;
        this.incorrectCategory = Category.CPU;

        this.repository.save(peripheral);

        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(peripheral.getId(), incorrectCategory);
        assertTrue(computerComponent.isEmpty());
        computerComponent = this.repository.fetchComponent(peripheral.getId(), correctCategory);
        assertTrue(computerComponent.isPresent());
        assertEquals(peripheral, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchMonitorByCategory() throws Exception {
        this.correctCategory = Category.MONITOR;
        this.incorrectCategory = Category.CPU;

        this.repository.save(monitor);

        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(monitor.getId(), incorrectCategory);
        assertTrue(computerComponent.isEmpty());
        computerComponent = this.repository.fetchComponent(monitor.getId(), correctCategory);
        assertTrue(computerComponent.isPresent());
        assertEquals(monitor, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchMemoryByCategory() throws Exception {
        this.correctCategory = Category.MEMORY;
        this.incorrectCategory = Category.CPU;

        this.repository.save(memory);

        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(memory.getId(), incorrectCategory);
        assertTrue(computerComponent.isEmpty());
        computerComponent = this.repository.fetchComponent(memory.getId(), correctCategory);
        assertTrue(computerComponent.isPresent());
        assertEquals(memory, computerComponent.get());
    }

    @Test
    public void testUploadAndFetchExternalMemoryByCategory() throws Exception {
        this.correctCategory = Category.STORAGE;
        this.incorrectCategory = Category.CPU;

        this.repository.save(externalMemory);

        Optional<ComputerComponent> computerComponent = this.repository.fetchComponent(externalMemory.getId(), incorrectCategory);
        assertTrue(computerComponent.isEmpty());
        computerComponent = this.repository.fetchComponent(externalMemory.getId(), correctCategory);
        assertTrue(computerComponent.isPresent());
        assertEquals(externalMemory, computerComponent.get());
    }
}
