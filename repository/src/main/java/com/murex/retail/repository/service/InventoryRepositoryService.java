package com.murex.retail.repository.service;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.parser.InventoryParser;
import com.murex.retail.repository.exceptions.ComponentNotFoundException;
import com.murex.retail.repository.exceptions.ComponentSaveException;
import com.murex.retail.repository.exceptions.RepositoryException;
import com.murex.retail.repository.hibernate.inventory.HibernateInventoryRepository;
import com.murex.retail.repository.inmemory.inventory.InMemoryInventoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepositoryService {
    private static final Logger LOG = LogManager.getLogger(InventoryRepositoryService.class);
    private final InMemoryInventoryRepository inMemoryRepo;
    private final HibernateInventoryRepository databaseRepo;

    public InventoryRepositoryService() {
        this.inMemoryRepo = new InMemoryInventoryRepository();
        this.databaseRepo = new HibernateInventoryRepository();
        this.initialize();
    }

    public void initialize() {
        try {
            List<ComputerComponent> parsedComponents = InventoryParser.parseInventory("Inventory.csv");
            this.inMemoryRepo.saveAll(parsedComponents);
            this.flushCacheToDB();
        } catch (Exception e) {
            String message = "Database could not be initialised.";
            throw new IllegalStateException(message, e);
        }
    }

    public void save(ComputerComponent computerComponent) throws ComponentSaveException {
        try {
            this.inMemoryRepo.save(computerComponent);

        } catch (Exception e) {
            String message = "Unable to save component with id: " + computerComponent.getId();
            LOG.error(message, e);
            throw new ComponentSaveException(message);
        }
    }

    public void saveAll(List<ComputerComponent> computerComponents) throws ComponentSaveException {
        try {
            this.inMemoryRepo.saveAll(computerComponents);
        } catch (Exception e) {
            String message = "Unable to save total of: " + computerComponents.size() + " components to database.";
            LOG.error(message, e);
            throw new ComponentSaveException(message);
        }
    }

    public ComputerComponent fetchComponentByName(String name) throws ComponentNotFoundException {
        Optional<ComputerComponent> computerComponent;
        try {
            computerComponent = this.databaseRepo.fetchComponentByName(name);
            if (computerComponent.isPresent()) {
                return computerComponent.get();
            } else {
                String message = "Unable to fetch component with name: " + name;
                throw new IllegalArgumentException(message);
            }
        } catch (Exception e) {
            String message = "Unable to fetch component with name: " + name;
            LOG.error(message, e);
            throw new ComponentNotFoundException(message, e);
        }
    }


    public ComputerComponent fetchComponent(String id, Category category) throws ComponentNotFoundException {
        try {
            Optional<ComputerComponent> computerComponent = this.inMemoryRepo.fetchComponent(id, category);
            if (computerComponent.isPresent()) {
                return computerComponent.get();
            }
            computerComponent = this.databaseRepo.fetchComponent(id, category);
            if (computerComponent.isPresent()) {
                return computerComponent.get();
            } else {
                String message = "Unable to fetch component with id: " + id + " and category: " + category;
                throw new IllegalArgumentException(message);
            }
        } catch (Exception e) {
            String message = "Unable to fetch component with id: " + id + " and Category: " + category;
            LOG.error(message, e);
            throw new ComponentNotFoundException(message, e);
        }
    }

    public ComputerComponent fetchComponent(String id) throws ComponentNotFoundException {
        try {
            Optional<ComputerComponent> computerComponent = this.inMemoryRepo.fetchComponent(id);
            if (computerComponent.isPresent()) {
                return computerComponent.get();
            }
            computerComponent = this.databaseRepo.fetchComponent(id);
            if (computerComponent.isPresent()) {
                return computerComponent.get();
            } else {
                String message = "Unable to fetch component with id: " + id;
                throw new IllegalArgumentException(message);
            }
        } catch (Exception e) {
            String message = "Unable to fetch component with id: " + id;
            LOG.error(message, e);
            throw new ComponentNotFoundException(message, e);
        }
    }

    public List<ComputerComponent> fetchAll() throws RepositoryException {
        this.inMemoryRepo.saveAll(this.fetchComponentsFromDB());
        return this.inMemoryRepo.fetchAll();
    }

    private List<ComputerComponent> fetchComponentsFromDB() throws RepositoryException {
        return this.databaseRepo.fetchAll();
    }

    public void flushCacheToDB() throws RepositoryException {
        this.databaseRepo.saveAll(inMemoryRepo.fetchAll());
    }

    public int countItems() {
        return this.inMemoryRepo.fetchAll().size();
    }

    public void updateComponentStock(String id, int quantity) throws Exception {
        this.fetchComponent(id).decrementQuantity(quantity);
    }
}
