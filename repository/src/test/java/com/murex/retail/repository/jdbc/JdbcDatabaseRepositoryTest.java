package com.murex.retail.repository.jdbc;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.DatabaseTerminator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcDatabaseRepositoryTest {
    private JdbcDatabaseRepository repository;
    private final JdbcDatabaseInitializer initializer = new JdbcDatabaseInitializer();
    private final JdbcUtilityClass instance = JdbcUtilityClass.getInstance();
    private final DatabaseTerminator terminator = new JdbcDatabaseTerminator();
    private static ComputerComponent processor;
    private static ComputerComponent monitor;
    private static ComputerComponent peripheral;
    private static ComputerComponent memory;
    private static ComputerComponent externalMemory;
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

        builder.id("97222f34-2e84-48a3-a165-97962cdc8c95")
                .category(Category.MONITOR)
                .name("Asus VP229HA")
                .brand("Asus")
                .price(Double.parseDouble("130"))
                .quantity(Integer.parseInt("20"));
        monitor = builder.build();

        builder.id("cf6ed8eb-fe36-44a4-be81-08351cde0610")
                .category(Category.MOUSE)
                .name("Cougar 450M Gaming Mouse")
                .brand("Cougar")
                .price(Double.parseDouble("43"))
                .quantity(Integer.parseInt("10"));
        peripheral = builder.build();

        builder.id("710c3100-4a24-4f9a-947a-710948dea83")
                .category(Category.MEMORY)
                .name("Kingston HyperX Impact DDR4")
                .brand("Kingston")
                .price(Double.parseDouble("95"))
                .quantity(Integer.parseInt("10"));
        memory = builder.build();

        builder.id("75cfcec-9655-4c68-9afc-8c706685c883")
                .category(Category.STORAGE)
                .name("Kingston Digital KC1000 NVMe PCIe")
                .brand("Kingston")
                .price(Double.parseDouble("439"))
                .quantity(Integer.parseInt("12"));
        externalMemory = builder.build();
    }

    @BeforeEach
    public void setup() {
        this.repository = new JdbcDatabaseRepository();
    }

    @AfterEach
    public void breakDown() {
        this.terminator.terminate(ComponentTable.values());
    }

    @Test
    public void testUploadAndFetchProcessor() throws Exception {
        this.repository.save(processor);
        assertEquals(processor, this.repository.fetchComponent(processor.getId()).get());
    }

    @Test
    public void testUploadAndFetchPeripheral() throws Exception {
        this.repository.save(peripheral);
        assertEquals(peripheral, this.repository.fetchComponent(peripheral.getId()).get());
    }

    @Test
    public void testUploadAndFetchMonitor() throws Exception {
        this.repository.save(monitor);
        assertEquals(monitor, this.repository.fetchComponent(monitor.getId()).get());
    }

    @Test
    public void testUploadAndFetchMemory() throws Exception {
        this.repository.save(memory);
        assertEquals(memory, this.repository.fetchComponent(memory.getId()).get());
    }

    @Test
    public void testUploadAndFetchExternalMemory() throws Exception {
        this.repository.save(externalMemory);
        assertEquals(externalMemory, this.repository.fetchComponent(externalMemory.getId()).get());
    }

    @Test
    public void testUploadAndFetchProcessorByCategory() throws Exception {
        this.correctCategory = Category.CPU;

        this.repository.save(processor);

        assertEquals(processor, this.repository.fetchComponent(processor.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchPeripheralByCategory() throws Exception {
        this.correctCategory = Category.KEYBOARD;

        this.repository.save(peripheral);

        assertEquals(peripheral, this.repository.fetchComponent(peripheral.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchMonitorByCategory() throws Exception {
        this.correctCategory = Category.MONITOR;

        this.repository.save(monitor);

        assertEquals(monitor, this.repository.fetchComponent(monitor.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchMemoryByCategory() throws Exception {
        this.correctCategory = Category.MEMORY;

        this.repository.save(memory);

        assertEquals(memory, this.repository.fetchComponent(memory.getId(), this.correctCategory).get());
    }

    @Test
    public void testUploadAndFetchExternalMemoryByCategory() throws Exception {
        this.correctCategory = Category.STORAGE;

        this.repository.save(externalMemory);

        assertEquals(externalMemory, this.repository.fetchComponent(externalMemory.getId(), this.correctCategory).get());
    }
}