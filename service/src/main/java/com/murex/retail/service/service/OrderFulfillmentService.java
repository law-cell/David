package com.murex.retail.service.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.service.OrderRepositoryService;
import com.murex.retail.service.dispatcher.OrderDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderFulfillmentService {
    private static final Logger LOGGER = LogManager.getLogger(OrderFulfillmentService.class);
    public static final int ONE = 1;
    private final InventoryService inventoryService;
    private final OrderRepositoryService orderRepositoryService;
    private final OrderDispatcher orderDispatcher;
    private final ExecutorService executorService;

    @Autowired
    public OrderFulfillmentService(InventoryService inventoryService, OrderRepositoryService orderRepositoryService,
                                   OrderDispatcher orderDispatcher) {
        this.inventoryService = inventoryService;
        this.orderRepositoryService = orderRepositoryService;
        this.orderDispatcher = orderDispatcher;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public Order createOrder(List<String> idList) throws Exception {
        Map<String, Integer> componentQuantityMap = getComponentQuantity(idList);
        if (checkStock(componentQuantityMap)) {
            Order order = new Order();
            order.setIdList(idList);
            LOGGER.info("Order '" + order.getId() + "' received for component IDs: " + idList);
            this.inventoryService.updateStock(componentQuantityMap);
            this.orderRepositoryService.save(order);
            this.orderDispatcher.add(order);
            this.executorService.execute(this.orderDispatcher);
            return order;
        }
        String message = "Not enough stock for item(s) in list '" + idList + "'.";
        LOGGER.info(message);
        throw new IllegalStateException(message);
    }

    private Map<String, Integer> getComponentQuantity(List<String> idList) {
        Map<String, Integer> componentQuantityMap = new HashMap<>();
        for (String id : idList) {
            if (componentQuantityMap.containsKey(id)) {
                componentQuantityMap.put(id, componentQuantityMap.get(id) + ONE);
            } else {
                componentQuantityMap.put(id, ONE);
            }
        }

        return componentQuantityMap;
    }

    private boolean checkStock(Map<String, Integer> componentQuantityMap) throws Exception {
        for (String id : componentQuantityMap.keySet()) {
            if (componentQuantityMap.get(id) > this.inventoryService
                    .fetchComponent(id)
                    .getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
