package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentMapOutput;
import com.murex.retail.operations.ComponentOperations;

import java.util.List;

public class FindMostExpensiveComponentOfEachCategory implements QueryStrategy {

    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        ComponentMapOutput mostExpensiveComponents =
                ComponentOperations.findMostExpensiveOfEachComponentCategory(computerComponents);
        return new MapResult(mostExpensiveComponents);
    }
}
