package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;

import java.util.List;

public interface QueryStrategy {
    QueryResult query(List<ComputerComponent> computerComponents);
}
