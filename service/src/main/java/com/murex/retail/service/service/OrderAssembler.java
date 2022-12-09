package com.murex.retail.service.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.model.order.OrderStatus;
import com.murex.retail.repository.service.OrderRepositoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAssembler {
    private static final Logger LOGGER = LogManager.getLogger(OrderAssembler.class);
    private static OrderAssembler instance = null;
    @Autowired
    private OrderRepositoryService orderRepositoryService;
    private static final long ASSEMBLY_TIME = 30000;
    private int ordersAssembled;
    private long averageAssemblyTime;

    private OrderAssembler() {
        this.ordersAssembled = 0;
        this.averageAssemblyTime = 0;
    }

    public static OrderAssembler getInstance() {
        if (instance == null) {
            instance = new OrderAssembler();
        }
        return instance;
    }

    public void assembleOrder(Order order) throws Exception {
        LOGGER.info("Begin assembling order '" + order.getId() + "'...");
        order.setStatus(OrderStatus.IN_PROGRESS);
        this.orderRepositoryService.update(order);
        Thread.sleep(ASSEMBLY_TIME);
        order.setStatus(OrderStatus.READY);
        long assemblyTime = System.currentTimeMillis() - order.getStartTime();
        order.setAssemblyTime(assemblyTime);
        this.orderRepositoryService.update(order);
        LOGGER.info("Order '" + order.getId() + "' assembled successfully in: " + order.getAssemblyTime());
        this.averageAssemblyTime = averageAssemblyTime + ((assemblyTime - averageAssemblyTime) / ++ordersAssembled);
        LOGGER.info("Average assembly time: " + averageAssemblyTime);
    }
}