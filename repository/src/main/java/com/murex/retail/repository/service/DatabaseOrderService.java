package com.murex.retail.repository.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.exceptions.ComponentNotFoundException;
import com.murex.retail.repository.exceptions.ComponentSaveException;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import com.murex.retail.repository.inmemory.order.InMemoryOrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
public class DatabaseOrderService {
    private static final Logger LOG = LogManager.getLogger(DatabaseOrderService.class);
    private InMemoryOrderRepository inMemoryOrderRepository;
    private HibernateOrderRepository hibernateOrderRepository;

    public DatabaseOrderService() {
        initialize();
    }

    private void initialize() {
        this.inMemoryOrderRepository = new InMemoryOrderRepository();
        this.hibernateOrderRepository = new HibernateOrderRepository();
    }

    public void save(Order order) throws ComponentSaveException {
        try {
            this.inMemoryOrderRepository.save(order);
        } catch (Exception e) {
            String message = format("Unable to save order with id: %s", order.getId());
            LOG.error(message, e);
            throw new ComponentSaveException(message);
        }
    }

    public Order fetchOrder(String id) throws ComponentNotFoundException {
        LOG.info("Fetching order with ID: " + id + "...");
        try {
            Optional<Order> order = this.inMemoryOrderRepository.fetchOrder(id);
            String message;
            if (order.isPresent()) {
                message = "Order '" + id + "' fetched successfully.";
                LOG.info(message);
                return order.get();
            }
            order = this.hibernateOrderRepository.fetchOrder(id);
            if (order.isPresent()) {
                message = "Order '" + id + "' fetched successfully.";
                LOG.info(message);
                return order.get();
            } else {
                message = format("Unable to fetch order with id: %s", id);
                throw new IllegalArgumentException(message);
            }
        } catch (Exception e) {
            String message = format("Unable to fetch order with id: %s", id);
            LOG.error(message, e);
            throw new ComponentNotFoundException(message, e);
        }
    }

    public List<Order> fetchAll() throws Exception {
        this.inMemoryOrderRepository.saveAll(this.fetchComponentsFromDB());
        return this.inMemoryOrderRepository.fetchAll();
    }

    private List<Order> fetchComponentsFromDB() throws Exception {
        return this.hibernateOrderRepository.fetchAll();
    }

    public void flushCacheToDB() throws Exception {
        this.hibernateOrderRepository.saveAll(inMemoryOrderRepository.fetchAll());
    }
}