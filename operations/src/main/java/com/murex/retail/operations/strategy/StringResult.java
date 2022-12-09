package com.murex.retail.operations.strategy;
public class StringResult implements QueryResult {
    private final String result;
    public StringResult(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }
}
