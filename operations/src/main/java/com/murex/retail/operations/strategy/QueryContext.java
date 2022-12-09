package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;

import java.util.List;

public class QueryContext {
    private QueryStrategy queryStrategy;

    public void setStrategy(QueryStrategy queryStrategy) {
        this.queryStrategy = queryStrategy;
    }

    public QueryResult executeStrategy(List<ComputerComponent> computerComponents) {
        return queryStrategy.query(computerComponents);
    }
}
