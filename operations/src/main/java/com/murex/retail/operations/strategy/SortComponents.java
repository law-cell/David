package com.murex.retail.operations.strategy;

import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.ComponentOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SortComponents implements QueryStrategy{
    private static final Logger LOG = LogManager.getLogger(SortComponents.class);
    @Override
    public QueryResult query(List<ComputerComponent> computerComponents) {
        LOG.info("Begin sorting components...");
        ComponentOperations.sortComponents(computerComponents);
        return new StringResult("Finished sorting components");
    }
}
