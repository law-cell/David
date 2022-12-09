package com.murex.retail.service.service;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.service.OrderRepositoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrderServiceTest {
    @MockBean
    private OrderRepositoryService orderRepositoryService;
    @Autowired
    private OrderService orderService;
    @MockBean
    OrderFulfillmentService orderFulfillmentService;
    @MockBean
    OrderAssembler orderAssembler;
    private static Order order;
    private static final String ORDER_ID = "order-id";

    @BeforeAll
    public static void setup() {
        List<String> idList = new ArrayList<>() {{
            add("70d0c37e-634e-4a68-8862-0ba44f216f3b");
        }};
        order = new Order();
        order.setId(ORDER_ID);
        order.setIdList(idList);
    }

    @Test
    void testSuccessfulOrderCreated() throws Exception {
        when(this.orderRepositoryService.fetchOrder(ORDER_ID)).thenReturn(Optional.of(order));
        assertEquals(order, this.orderService.fetchOrder(ORDER_ID));
    }

    @Test
    void testWhenOrderNotCreated() throws Exception {
        String invalidId = "invalid-id";
        when(this.orderRepositoryService.fetchOrder(invalidId))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> this.orderService.fetchOrder(invalidId));
    }

    @Test
    void testBuildOrder() throws Exception {
        when(this.orderFulfillmentService.createOrder(order.getIdList())).thenReturn(order);
        assertEquals(order.getId(), this.orderService.buildOrder(order.getIdList()));
    }
}
