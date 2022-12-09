package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;

import java.util.List;

public class FindCheapestComponent implements QueryStrategy {
    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        ComputerComponent cheapestComponent = ComponentOperations.findCheapestComponent(computerComponents);
        return new ComputerComponentResult(cheapestComponent);
    }
}
