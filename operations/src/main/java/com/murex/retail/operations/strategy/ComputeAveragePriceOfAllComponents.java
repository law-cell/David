package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;

import java.util.List;

public class ComputeAveragePriceOfAllComponents implements QueryStrategy {

    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        double avgPrice = ComponentOperations.computeAverageComponentPrice(computerComponents);
        return new DoubleResult(avgPrice);
    }
}
