package com.murex.retail.service.controller;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import com.murex.retail.repository.inmemory.order.InMemoryOrderRepository;
import com.murex.retail.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@ComponentScan(basePackageClasses = {InMemoryOrderRepository.class, HibernateOrderRepository.class})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/build")
    public ResponseEntity<String> buildOrder(@RequestBody List<String> idList) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.orderService.buildOrder(idList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> fetchOrder(@PathVariable String id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.orderService.fetchOrder(id));
    }
}