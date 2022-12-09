package com.murex.retail.repository.hibernate;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateOrderRepositoryTest {
    private final HibernateOrderRepository repository = new HibernateOrderRepository();
    private static Order order;
    private static final String ORDER_ID = "order-id";
    private final HibernateDatabaseTerminator terminator = new HibernateDatabaseTerminator();

    @BeforeAll
    public static void buildComponent() {
        List<String> ids = new ArrayList<>() {{
            add("70d0c37e-634e-4a68-8862-0ba44f216f3b");
        }};

        order = new Order();
        order.setIdList(ids);
        order.setId(ORDER_ID);
    }

    @AfterEach
    public void breakDown() throws Exception {
        terminator.terminateOrder();
    }

    @Test
    public void testSaveAndFetchOrder() throws Exception {
        assertEquals(Optional.empty(), this.repository.fetchOrder(ORDER_ID));
        this.repository.save(order);
        Optional<Order> optionalOrder = this.repository.fetchOrder(ORDER_ID);
        assertTrue(optionalOrder.isPresent());
        assertEquals(order.getId(), optionalOrder.get().getId());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        this.repository.save(order);
        Optional<Order> optionalOrder = this.repository.fetchOrder(ORDER_ID);
        assertTrue(optionalOrder.isPresent());

        Order updatedOrder = new Order();
        updatedOrder.setId(ORDER_ID);
        List<String> expectedComponentIdList = new ArrayList<>() {{
            add("updated-list");
        }};
        updatedOrder.setIdList(expectedComponentIdList);
        this.repository.update(updatedOrder);

        optionalOrder = this.repository.fetchOrder(ORDER_ID);
        assertTrue(optionalOrder.isPresent());
        updatedOrder = optionalOrder.get();
        assertEquals(expectedComponentIdList, updatedOrder.getIdList());
    }
}

