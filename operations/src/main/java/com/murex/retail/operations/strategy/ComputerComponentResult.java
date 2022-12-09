package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;

public class ComputerComponentResult implements QueryResult {
    private final ComputerComponent result;
    private final String type = "component";

    public ComputerComponentResult() {
        result = getResult();
    }

    public ComputerComponentResult(ComputerComponent result) {
        this.result = result;
    }

    @Override
    public ComputerComponent getResult() {
        return result;
    }
}
