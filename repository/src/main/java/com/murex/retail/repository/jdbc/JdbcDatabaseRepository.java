package com.murex.retail.repository.jdbc;

import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.DatabaseRepository;
import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class JdbcDatabaseRepository implements DatabaseRepository {
    private static final Logger LOG = LogManager.getLogger(JdbcDatabaseRepository.class);
    private final JdbcUtilityClass instance;

    public JdbcDatabaseRepository() {
        this.instance = JdbcUtilityClass.getInstance();
        JdbcDatabaseInitializer initializer = new JdbcDatabaseInitializer();
        initializer.initialize();
    }

    @Override
    public void save(ComputerComponent computerComponent) throws JdbcRepositoryException {
        String componentId = computerComponent.getId();
        LOG.info("Inserting component with ID '" + componentId + "' into DB...");
        try (Statement stmt = instance.getConnection()
                .createStatement()) {
            Category category = computerComponent.getCategory();
            String query = JdbcStatementUtilities.insertComponent(computerComponent, ComponentTable.getComponentTable(category));
            stmt.execute(query);
            LOG.info("Successfully saved component '" + componentId + "' into table.");
        } catch (Exception e) {
            LOG.error("Could not save component '" + componentId + "'  into table.", e);
            throw new JdbcRepositoryException("", e);
        }
    }

    @Override
    public void saveAll(List<ComputerComponent> computerComponents) throws JdbcRepositoryException {
        for (ComputerComponent computerComponent : computerComponents) {
            this.save(computerComponent);
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id) throws JdbcRepositoryException {
        ComputerComponent computerComponent = null;
        LOG.info("Fetching component with ID '" + id + "'...");
        try (Statement stmt = instance.getConnection()
                .createStatement()) {
            EnumSet<ComponentTable> componentTableSet = EnumSet.allOf(ComponentTable.class);
            for (ComponentTable componentTable : componentTableSet) {
                String query = JdbcStatementUtilities.retrieveComponent(componentTable, id);
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    computerComponent = ResultSetParser.parseRow(rs);
                    LOG.info("Component '" + id + "' successfully retrieved from table.");
                }
            }

            return Optional.ofNullable(computerComponent);
        } catch (Exception e) {
            throw new JdbcRepositoryException("Could not read from table while trying to fetch component '" + id + "'.", e);
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id, Category category) throws JdbcRepositoryException {
        ComponentTable componentTable = ComponentTable.getComponentTable(category);
        LOG.info("Fetching component with ID '" + id + "'...");
        try (Statement stmt = instance.getConnection()
                .createStatement()) {
            String query = JdbcStatementUtilities.retrieveComponent(componentTable, id);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                LOG.info("Component '" + id + "' successfully retrieved from table.");
                return Optional.of(ResultSetParser.parseRow(rs));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new JdbcRepositoryException("Could not read from table while trying to fetch component '" + id + "'.", e);
        }
    }

    @Override
    public List<ComputerComponent> fetchAll() throws JdbcRepositoryException {
        List<ComputerComponent> computerComponents = new ArrayList<>();
        try (Statement stmt = instance.getConnection()
                .createStatement()) {
            EnumSet<ComponentTable> componentTableSet = EnumSet.allOf(ComponentTable.class);
            for (ComponentTable componentTable : componentTableSet) {
                LOG.info("Retrieving all components from table '" + componentTable + "'...");
                String query = JdbcStatementUtilities.retrieveComponents(componentTable);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    computerComponents.add(ResultSetParser.parseRow(rs));
                }
            }
            return computerComponents;
        } catch (Exception e) {
            throw new JdbcRepositoryException("Components could not be read from corresponding categories.", e);
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponentByName(String name) {
        return Optional.empty();
    }
}