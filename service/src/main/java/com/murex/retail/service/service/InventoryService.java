package com.murex.retail.service.service;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.strategy.ComputeAveragePriceOfAllComponents;
import com.murex.retail.operations.strategy.ComputeAveragePriceOfCPUs;
import com.murex.retail.operations.strategy.DoubleResult;
import com.murex.retail.operations.strategy.QueryContext;
import com.murex.retail.repository.service.InventoryRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@ComponentScan(basePackageClasses = {InventoryRepositoryService.class})
public class InventoryService {
    private final InventoryRepositoryService inventoryRepositoryService;
    private final QueryContext queryContext;

    @Autowired
    public InventoryService(InventoryRepositoryService inventoryRepositoryService) {
        this.queryContext = new QueryContext();
        this.inventoryRepositoryService = inventoryRepositoryService;
    }

    public ComputerComponent fetchComponent(String id) throws Exception {
        return this.inventoryRepositoryService.fetchComponent(id);
    }

    public ComputerComponent fetchComponentByName(String name) throws Exception {
        return this.inventoryRepositoryService.fetchComponentByName(name);
    }

    public List<ComputerComponent> fetchAll() throws Exception {
        return this.inventoryRepositoryService.fetchAll();
    }

    public double computeAveragePrice() throws Exception {
        this.queryContext.setStrategy(new ComputeAveragePriceOfAllComponents());
        DoubleResult queryOutput = (DoubleResult) queryContext.executeStrategy(this.fetchAll());
        return queryOutput.getResult();
    }

    public double computeAverageCpuPrice() throws Exception {
        this.queryContext.setStrategy(new ComputeAveragePriceOfCPUs());
        DoubleResult queryOutput = (DoubleResult) queryContext.executeStrategy(this.fetchAll());
        return queryOutput.getResult();
    }

    public int countItems() {
        return this.inventoryRepositoryService.countItems();
    }

    public void updateStock(Map<String, Integer> componentQuantityMap) throws Exception {
        for (String id : componentQuantityMap.keySet()) {
            this.inventoryRepositoryService.updateComponentStock(id, componentQuantityMap.get(id));
        }
    }
}