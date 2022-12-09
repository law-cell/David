package com.murex.retail.operations;

import com.murex.retail.model.component.Category;

import java.util.Map;

public class LongMapOutput implements MapOutput {
    private final Map<Category, Long> result;
    private final String type = "long";

    public LongMapOutput() {
        this.result = getResult();
    }

    LongMapOutput(Map<Category, Long> result) {
        this.result = result;
    }

    @Override
    public Map<Category, Long> getResult() { return this.result; }
}
