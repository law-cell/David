package com.murex.retail.repository.inmemory.inventory;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.DatabaseRepository;
import com.murex.retail.repository.inmemory.InMemoryRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryInventoryRepository implements DatabaseRepository {
    private final static Logger LOG = LogManager.getLogger(InMemoryInventoryRepository.class);
    private static Map<String, ComputerComponent> componentMap;

    public InMemoryInventoryRepository() {
        componentMap = new HashMap<>();
    }

    @Override
    public void save(ComputerComponent computerComponent) {
        componentMap.put(computerComponent.getId(), computerComponent);
    }

    @Override
    public void saveAll(List<ComputerComponent> computerComponents) {
        computerComponents.forEach(this::save);
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id) {
        return Optional.ofNullable(componentMap.get(id));
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id, Category category) throws InMemoryRepositoryException {
        try {
            Optional<ComputerComponent> computerComponent = Optional.ofNullable(componentMap.get(id));
            if (computerComponent.get().getCategory() == category) {
                return computerComponent;
            }
            return Optional.empty();
        } catch (Exception e) {
            String message = "Unable to find component with id: " + id + " and category: " + category;
            LOG.error(message, e);
            throw new InMemoryRepositoryException(message, e);
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponentByName(String name) {
        List<ComputerComponent> computerComponents = this.fetchAll();
        List<ComputerComponent> fetchComponent = computerComponents.stream()
                .filter(computerComponent -> computerComponent.getName().equals(name))
                .collect(Collectors.toList());
        return Optional.of(fetchComponent.get(0));
    }

    @Override
    public List<ComputerComponent> fetchAll() {
        return new ArrayList<>(componentMap.values());
    }
}