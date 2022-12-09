package com.murex.retail.service.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.service.OrderRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackageClasses = {OrderRepositoryService.class})
public class OrderService {
    private final OrderRepositoryService orderRepositoryService;
    private final OrderFulfillmentService orderFulfillmentService;

    @Autowired
    public OrderService(OrderRepositoryService orderRepositoryService, OrderFulfillmentService orderFulfillmentService) {
        this.orderRepositoryService = orderRepositoryService;
        this.orderFulfillmentService = orderFulfillmentService;
    }

    public Order fetchOrder(String id) throws Exception {
        Optional<Order> optionalOrder = this.orderRepositoryService.fetchOrder(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        throw new IllegalStateException("Unable to fetch order with ID: " + id + "\nPlease try again.");
    }

    public List<Order> fetchAll() throws Exception {
        return this.orderRepositoryService.fetchAll();
    }

    public String buildOrder(List<String> idList) throws Exception {
        Order order = this.orderFulfillmentService.createOrder(idList);
        return order.getId();
    }
}