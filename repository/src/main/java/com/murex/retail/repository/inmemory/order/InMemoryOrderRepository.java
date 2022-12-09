package com.murex.retail.repository.inmemory.order;

import com.murex.retail.model.order.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository {
    private final Map<String, Order> orderMap;

    public InMemoryOrderRepository() {
        this.orderMap = new HashMap<>();
    }

    public void save(Order order) {
        this.orderMap.put(order.getId(), order);
    }

    public void saveAll(List<Order> orders) {
        orders.forEach(this::save);
    }

    public Optional<Order> fetchOrder(String id) {
        return Optional.ofNullable(this.orderMap.get(id));
    }

    public List<Order> fetchAll() {
        return new ArrayList<>(this.orderMap.values());
    }
}