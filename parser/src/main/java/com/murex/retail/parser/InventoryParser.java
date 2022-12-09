package com.murex.retail.parser;


import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.model.factory.ComponentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryParser {
    private static final Logger LOG = LogManager.getLogger(InventoryParser.class);

    public static List<ComputerComponent> parseInventory(String path) {
        try {
            LOG.info("Converting CSV to component objects...");
            List<ComputerComponent> computerComponents = CSVParser.parseCSV(path, true).stream()
                    .map(ComponentFactory::getComponent)
                    .collect(Collectors.toList());
            LOG.info("All components assembled successfully.");
            return computerComponents;
        } catch (Exception e) {
            throw new InventoryParserException("CSV file could not be parsed at path: '" + path + "'", e);
        }
    }
}
