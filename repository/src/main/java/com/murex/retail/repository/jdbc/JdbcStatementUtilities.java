package com.murex.retail.repository.jdbc;

import com.murex.retail.repository.ComponentTable;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.model.component.ExternalMemory;
import com.murex.retail.model.component.Memory;
import com.murex.retail.model.component.Monitor;
import com.murex.retail.model.component.Peripheral;
import com.murex.retail.model.component.Processor;

public class JdbcStatementUtilities {
    private JdbcStatementUtilities() {}

    public static String createTable(ComponentTable componentTable) throws IllegalArgumentException {
        StringBuilder dbStatement = new StringBuilder("CREATE TABLE IF NOT EXISTS " + componentTable.toString() + "(")
                .append("id VARCHAR(36) NOT NULL PRIMARY KEY,")
                .append("category VARCHAR(20) NOT NULL,")
                .append("name VARCHAR(50) NOT NULL,")
                .append("brand VARCHAR(30) NOT NULL,");

        switch (componentTable) {
            case PROCESSOR:
                dbStatement.append("product_line VARCHAR(40),")
                        .append("number_of_cores VARCHAR(20),")
                        .append("processor_clock_speed VARCHAR(30),")
                        .append("graphic_clock_speed VARCHAR(30),");
                break;
            case MONITOR:
                dbStatement.append("color VARCHAR(20),")
                        .append("dimension VARCHAR(30),")
                        .append("resolution VARCHAR(20),");
                break;
            case PERIPHERAL:
                dbStatement.append("color VARCHAR(20),")
                        .append("dimension VARCHAR(30),");
                break;
            case EXTERNAL_MEMORY:
                dbStatement.append("interface VARCHAR(15),")
                        .append("size VARCHAR(15),")
                        .append("dimension VARCHAR(30),");
                break;
            case MEMORY:
                dbStatement.append("interface VARCHAR(15),")
                        .append("size VARCHAR(15),");
                break;
            default:
                throw new IllegalArgumentException("An invalid category was specified.");
        }

        return dbStatement.append("price FLOAT(2) NOT NULL,")
                .append("quantity INT NOT NULL)").toString();
    }

    public static  String retrieveComponents(ComponentTable componentTable) throws IllegalArgumentException {
        return new StringBuilder("SELECT id, category, name, brand, ")
                .append(getComponentProperties(componentTable))
                .append("price, quantity FROM "+ componentTable)
                .toString();
    }

    public static  String retrieveComponent(ComponentTable componentTable, String id) throws IllegalArgumentException {
        return new StringBuilder("SELECT id, category, name, brand, ")
                .append(getComponentProperties(componentTable))
                .append("price, quantity FROM "+ componentTable)
                .append(" WHERE ID = '"+id+"'")
                .toString();
    }

    public static  String insertComponent(ComputerComponent computerComponent, ComponentTable componentTable) throws IllegalArgumentException {
        return new StringBuilder("INSERT INTO " + componentTable)
                .append("(id, category, name, brand, ")
                .append(getComponentProperties(componentTable))
                .append("price, quantity) ")
                .append(getValues(computerComponent))
                .toString();
    }

    private static String getValues(ComputerComponent computerComponent) {
        switch (computerComponent.getCategory()) {
            case CPU:
            case GPU:
                Processor processor = (Processor) computerComponent;
                return String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%f', '%d');",
                        processor.getId(), processor.getCategory(), processor.getName(), processor.getBrand(), processor.getProductLine(),
                        processor.getNumberOfCores(), processor.getProcessorClockSpeed(), processor.getGraphicsClockSpeed(),
                        processor.getPrice(), processor.getQuantity());
            case MONITOR:
                Monitor monitor = (Monitor) computerComponent;
                return String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%f', '%d');",
                        monitor.getId(), monitor.getCategory(), monitor.getName(), monitor.getBrand(),
                        monitor.getDimension(), monitor.getColor(), monitor.getResolution(),
                        monitor.getPrice(), monitor.getQuantity());
            case MOUSE:
            case KEYBOARD:
                Peripheral peripheral = (Peripheral) computerComponent;
                return String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%f', '%d');",
                        peripheral.getId(), peripheral.getCategory(), peripheral.getName(), peripheral.getBrand(),
                        peripheral.getDimension(), peripheral.getColor(), peripheral.getPrice(), peripheral.getQuantity());
            case STORAGE:
                ExternalMemory externalMemory = (ExternalMemory) computerComponent;
                return String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%f', '%d');",
                        externalMemory.getId(), externalMemory.getCategory(), externalMemory.getName(),
                        externalMemory.getBrand(), externalMemory.getInterface(), externalMemory.getSize(),
                        externalMemory.getDimension(), externalMemory.getPrice(), externalMemory.getQuantity());
            case MEMORY:
                Memory memory = (Memory) computerComponent;
                return String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%f', '%d');",
                        memory.getId(), memory.getCategory(), memory.getName(), memory.getBrand(),
                        memory.getInterface(), memory.getSize(), memory.getPrice(), memory.getQuantity());
            default:
                throw new IllegalArgumentException("An invalid category was specified.");
        }
    }

    private static String getComponentProperties(ComponentTable componentTable) throws IllegalArgumentException {
        StringBuilder componentProperties = new StringBuilder();
        switch (componentTable) {
            case PROCESSOR:
                componentProperties.append("product_line, number_of_cores, processor_clock_speed, graphic_clock_speed, ");
                break;
            case MONITOR:
                componentProperties.append("dimension, color, resolution, ");
                break;
            case PERIPHERAL:
                componentProperties.append("dimension, color, ");
                break;
            case EXTERNAL_MEMORY:
                componentProperties.append("interface, size, dimension, ");
                break;
            case MEMORY:
                componentProperties.append("interface, size, ");
                break;
            default:
                throw new IllegalArgumentException("An invalid category was specified.");
        }

        return componentProperties.toString();
    }
}