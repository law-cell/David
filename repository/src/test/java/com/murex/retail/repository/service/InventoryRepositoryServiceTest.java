package com.murex.retail.repository.service;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.DatabaseTerminator;
import com.murex.retail.repository.hibernate.HibernateDatabaseTerminator;
import com.murex.retail.repository.hibernate.HibernateUtilityClass;
import com.murex.retail.repository.hibernate.MetadataExtractorIntegrator;
import com.murex.retail.repository.hibernate.inventory.HibernateInventoryRepository;
import org.hibernate.boot.Metadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryRepositoryServiceTest {
    private final InventoryRepositoryService repository = new InventoryRepositoryService();
    private final DatabaseTerminator terminator = new HibernateDatabaseTerminator();
    private static final List<ComputerComponent> computerComponents = new ArrayList<>();
    private static ComputerComponent processor;
    private static ComponentBuilder builder;

    @BeforeAll
    public static void buildComponent() {
        builder = new ComponentBuilder();
        builder.id("70d0c37e-634e-4a68-8862-0ba44f216f3b")
                .category(Category.CPU)
                .name("Intel Core i7-8809G")
                .brand("Intel")
                .price(Double.parseDouble("150"))
                .quantity(Integer.parseInt("25"));
        processor = builder.build();
        computerComponents.add(processor);
    }

    @AfterEach
    public void breakDown() {
        this.terminator.terminate(ComponentTable.values());
    }

    @Test
    public void testComponentTablesExist() {
        List<String> expectedTables = new ArrayList<>() {{
            add("COMPUTER_COMPONENT");
            add("PROCESSOR");
            add("PERIPHERAL");
            add("MONITOR");
            add("MEMORY");
            add("EXTERNAL_MEMORY");
            add("ORDER_TABLE");
        }};
        Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
        List<String> tables = metadata.getEntityBindings()
                .stream()
                .map(e -> e.getTable().getName())
                .collect(Collectors.toList());

        Collections.sort(expectedTables);
        Collections.sort(tables);
        assertEquals(expectedTables, tables);
    }

    @Test
    public void testFlushToCache() throws Exception {
        HibernateInventoryRepository hibernateRepo = HibernateInventoryRepository.getRepository();
        hibernateRepo.saveAll(computerComponents);
        this.repository.saveAll(computerComponents);

        ComputerComponent computerComponent = repository.fetchComponent(processor.getId());
        computerComponent.setName("UPDATED NAME");
        repository.save(computerComponent);

        Optional<ComputerComponent> dbComponent = hibernateRepo.fetchComponent(computerComponent.getId());
        assertTrue(dbComponent.isPresent());
        assertNotSame(computerComponent.getName(), dbComponent.get().getName());

        repository.flushCacheToDB();
        dbComponent = hibernateRepo.fetchComponent(computerComponent.getId());
        assertTrue(dbComponent.isPresent());
        assertEquals(computerComponent.getName(), dbComponent.get().getName());
    }

    @Test
    public void testUpdateComponentStock() throws Exception {
        builder.id("70d0c37e-634e-4a68-8862-0ba44f216f3b")
                .category(Category.CPU)
                .name("Intel Core i7-8809G")
                .brand("Intel")
                .price(Double.parseDouble("150"))
                .quantity(Integer.parseInt("2"));
        ComputerComponent computerComponent = builder.build();
        String componentId = computerComponent.getId();
        this.repository.save(computerComponent);
        this.repository.updateComponentStock(componentId, 2);
        assertEquals(0, this.repository
                .fetchComponent(componentId)
                .getQuantity());
    }
}