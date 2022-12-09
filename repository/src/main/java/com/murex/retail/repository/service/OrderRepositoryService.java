package com.murex.retail.repository.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.exceptions.ComponentSaveException;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import com.murex.retail.repository.inmemory.order.InMemoryOrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryService {
    private static final Logger LOG = LogManager.getLogger(OrderRepositoryService.class);
    private InMemoryOrderRepository inMemoryOrderRepository;
    private HibernateOrderRepository hibernateOrderRepository;

    public OrderRepositoryService() {
        this.initialize();
    }

    public void initialize() {
        this.inMemoryOrderRepository = new InMemoryOrderRepository();
        this.hibernateOrderRepository = new HibernateOrderRepository();
    }

    public void save(Order order) throws ComponentSaveException {
        try {
            this.inMemoryOrderRepository.save(order);
            this.hibernateOrderRepository.save(order);
        } catch (Exception e) {
            String message = "Unable to save order with id: " + order.getId();
            LOG.error(message, e);
            throw new ComponentSaveException(message);
        }
    }

    public void update(Order order) throws Exception {
        try {
            this.inMemoryOrderRepository.save(order);
            this.hibernateOrderRepository.update(order);
        } catch (Exception e) {
            String message = "Unable to update order with id: " + order.getId();
            LOG.error(message, e);
            throw new ComponentSaveException(message);
        }
    }

    public Optional<Order> fetchOrder(String id) throws Exception {
        Optional<Order> optionalOrder = this.inMemoryOrderRepository.fetchOrder(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder;
        }
        optionalOrder = this.hibernateOrderRepository.fetchOrder(id);
        return optionalOrder;
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