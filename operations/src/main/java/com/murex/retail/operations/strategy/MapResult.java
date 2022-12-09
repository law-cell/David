package com.murex.retail.operations.strategy;

import com.murex.retail.operations.MapOutput;

public class MapResult implements QueryResult{
    private final MapOutput result;
    private final String type = "map";

    public MapResult() {
        result = getResult();
    }
    public MapResult(MapOutput result) {
        this.result = result;
    }

    @Override
    public MapOutput getResult() {
        return result;
    }
}
