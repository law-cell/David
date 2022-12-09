package com.murex.retail.repository.inmemory;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.inmemory.order.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryOrderRepositoryServiceTest {
    private final InMemoryOrderRepository repository = new InMemoryOrderRepository();
    private static Order order;
    private static final String ORDER_ID = "order-id";

    @BeforeAll
    public static void buildComponent() {
        List<String> ids = new ArrayList<>() {{
            add("70d0c37e-634e-4a68-8862-0ba44f216f3b");
        }};

        order = new Order();
        order.setIdList(ids);
        order.setId(ORDER_ID);
    }

    @Test
    public void testSaveAndFetchOrder() {
        assertEquals(Optional.empty(), this.repository.fetchOrder(ORDER_ID));
        this.repository.save(order);
        Optional<Order> output = this.repository.fetchOrder(ORDER_ID);
        assertTrue(output.isPresent());
        assertEquals(order, output.get());
    }
}

