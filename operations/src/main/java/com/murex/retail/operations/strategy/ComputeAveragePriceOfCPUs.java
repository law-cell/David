package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;

import java.util.List;

public class ComputeAveragePriceOfCPUs implements QueryStrategy {

    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        double avgPrice = ComponentOperations.computeAverageCPUPrice(computerComponents);
        return new DoubleResult(avgPrice);
    }
}
