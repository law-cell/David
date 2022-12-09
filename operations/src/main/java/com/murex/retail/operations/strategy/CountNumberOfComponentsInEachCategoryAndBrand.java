package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;
import com.murex.retail.operations.NestedMapOutput;

import java.util.List;

public class CountNumberOfComponentsInEachCategoryAndBrand implements QueryStrategy {

    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        NestedMapOutput numberOfComponents = ComponentOperations.reportNumberOfComponentsGroupedByCategoryAndBrand(computerComponents);
        return new MapResult(numberOfComponents);
    }
}
