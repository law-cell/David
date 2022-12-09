package com.murex.retail.repository.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderRepositoryServiceTest {
    private final OrderRepositoryService repository = new OrderRepositoryService();
    private static final String ORDER_ID = "test-id";
    private static List<String> idList;

    @BeforeAll
    public static void setup() {
        idList = new ArrayList<>() {{
            add("70d0c37e-634e-4a68-8862-0ba44f216f3b");
        }};
    }

    @Test
    public void testFlushToCache() throws Exception {
        HibernateOrderRepository hibernateRepo = HibernateOrderRepository.getRepository();
        Order order = new Order();
        order.setIdList(idList);
        order.setId(ORDER_ID);
        hibernateRepo.save(order);
        this.repository.save(order);

        order = repository.fetchOrder(order.getId()).get();
        order.setId("NEW_ID");
        repository.save(order);

        repository.flushCacheToDB();
        Optional<Order> dbOrder = hibernateRepo.fetchOrder("NEW_ID");
        assertTrue(dbOrder.isPresent());
        assertEquals(1, dbOrder.get()
                .getIdList()
                .size());
    }
}
