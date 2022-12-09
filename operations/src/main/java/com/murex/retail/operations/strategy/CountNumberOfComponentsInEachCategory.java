package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;
import com.murex.retail.operations.LongMapOutput;

import java.util.List;

public class CountNumberOfComponentsInEachCategory implements QueryStrategy {

    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        LongMapOutput numberOfComponents = ComponentOperations.reportNumberOfComponentsGroupedByCategory(computerComponents);
        return new MapResult(numberOfComponents);
    }
}
