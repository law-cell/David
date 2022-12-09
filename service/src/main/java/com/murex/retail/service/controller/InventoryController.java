package com.murex.retail.service.controller;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/computer_components")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping()
    public ResponseEntity<List<ComputerComponent>> fetchAll() throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.inventoryService.fetchAll());
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> countStoredItems() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.inventoryService.countItems());
    }

    @GetMapping
    @RequestMapping("/fetch/{name}")
    public ResponseEntity<ComputerComponent> fetchComponentByName(@PathVariable(value = "name") String name) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.inventoryService.fetchComponentByName(name));
    }

    @GetMapping
    @RequestMapping("operations/compute_average_price")
    public ResponseEntity<Double> computeAveragePrice() throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(inventoryService.computeAveragePrice());
    }

    @GetMapping
    @RequestMapping("operations/compute_average_cpu_price")
    public ResponseEntity<Double> computeAverageCpuPrice() throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(inventoryService.computeAverageCpuPrice());
    }
}
