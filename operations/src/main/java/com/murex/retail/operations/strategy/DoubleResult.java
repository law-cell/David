package com.murex.retail.operations.strategy;

public class DoubleResult implements QueryResult {
    private final double result;
    private final String type = "double";

    public DoubleResult() {
        result = getResult();
    }

    public DoubleResult(Double result) {
        this.result = result;
    }

    @Override
    public Double getResult() {
        return this.result;
    }
}
