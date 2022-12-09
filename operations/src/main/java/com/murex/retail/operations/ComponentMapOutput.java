package com.murex.retail.operations;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;

import java.util.Map;
import java.util.Optional;

public class ComponentMapOutput implements MapOutput {
    private final Map<Category, Optional<ComputerComponent>> result;
    private final String type = "component";

    public ComponentMapOutput() {
        this.result = getResult();
    }

    ComponentMapOutput(Map<Category, Optional<ComputerComponent>> result) {
        this.result = result;
    }

    @Override
    public Map<Category, Optional<ComputerComponent>> getResult() {
        return this.result;
    }

}
