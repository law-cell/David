package com.murex.retail.repository;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface DatabaseRepository {
    void save(ComputerComponent computerComponent) throws RepositoryException;

    void saveAll(List<ComputerComponent> comuterComponents) throws RepositoryException;

    Optional<ComputerComponent> fetchComponent(String id) throws RepositoryException;

    Optional<ComputerComponent> fetchComponent(String id, Category category) throws RepositoryException;

    List<ComputerComponent> fetchAll() throws RepositoryException;

    Optional<ComputerComponent> fetchComponentByName(String name) throws RepositoryException;
}
