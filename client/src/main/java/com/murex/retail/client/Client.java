package com.murex.retail.client;

import com.murex.retail.client.builder.InventoryFeignClientBuilder;
import com.murex.retail.client.feign.InventoryFeignClient;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.model.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final InventoryFeignClient inventoryClient = InventoryFeignClientBuilder.buildFeignClient(
            "http://localhost:8037/api/v1/computer_components", InventoryFeignClient.class);

    public static void main(String[] args) {
        List<ComputerComponent> computerComponents = inventoryClient.fetchAll();
        computerComponents.forEach(c -> LOGGER.info(c.getName()));

        int quantity = inventoryClient.countItems();
        LOGGER.info("Number of items: " + quantity);

        double averagePrice = inventoryClient.computeAveragePrice();
        LOGGER.info("Average component price: " + averagePrice);

        ComputerComponent computerComponent = inventoryClient.fetchComponentByName("FX-9590");
        LOGGER.info("Component: " + computerComponent);

        OrderClient orderClient = new OrderClient();
        List<String> componentIds = new ArrayList<>() {{
            add(computerComponents.get(0).getId());
            add(computerComponents.get(20).getId());
            add(computerComponents.get(39).getId());
            add(computerComponents.get(80).getId());
        }};

        String builtOrderId = orderClient.buildOrder(componentIds);
        LOGGER.info(": " + builtOrderId);
        Order builtOrder = orderClient.fetchOrder(builtOrderId);
        LOGGER.info("Completed order: " + builtOrder);
    }
}
