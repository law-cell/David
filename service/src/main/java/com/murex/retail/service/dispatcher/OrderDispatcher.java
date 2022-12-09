package com.murex.retail.service.dispatcher;


import com.murex.retail.model.order.Order;
import com.murex.retail.service.service.OrderAssembler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class OrderDispatcher implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger(OrderDispatcher.class);
    private final BlockingQueue<Order> blockingQueue;
    private final OrderAssembler orderAssembler;

    private OrderDispatcher() {
        this.orderAssembler = OrderAssembler.getInstance();
        this.blockingQueue = new LinkedBlockingQueue<>();
    }

    public void add(Order order) throws Exception {
        this.blockingQueue.put(order);
    }

    @Override
    public void run() {
        try {
            Order order = this.blockingQueue.take();
            this.orderAssembler.assembleOrder(order);
        } catch (Exception e) {
            String message = "Order could not be dispatched - thread interrupted.";
            LOGGER.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }
}
