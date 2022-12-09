package com.murex.retail.operations;

import com.murex.retail.model.component.Category;

import java.util.Map;

public class NestedMapOutput implements MapOutput {
    private final Map<Category, Map<String, Long>> result;
    private final String type = "nested";

    public NestedMapOutput() {
        this.result = getResult();
    }
    NestedMapOutput(Map<Category, Map<String, Long>> result) {
        this.result = result;
    }

    @Override
    public Map<Category, Map<String, Long>> getResult() {
        return this.result;
    }
}
