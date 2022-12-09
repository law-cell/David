package com.murex.retail.repository;

import com.murex.retail.model.component.Category;

public enum ComponentTable {
    PROCESSOR("Processor"),
    PERIPHERAL("Peripheral"),
    MONITOR("Monitor"),
    MEMORY("Memory"),
    EXTERNAL_MEMORY("ExternalMemory");

    private final String model;
    ComponentTable(String model) {
        this.model = model;
    }
    public String getModel() {
        return this.model;
    }


    public static ComponentTable getComponentTable(Category category) throws IllegalArgumentException {
        switch (category) {
            case CPU:
            case GPU:
                return ComponentTable.PROCESSOR;
            case MONITOR:
                return ComponentTable.MONITOR;
            case MOUSE:
            case KEYBOARD:
                return ComponentTable.PERIPHERAL;
            case STORAGE:
                return ComponentTable.EXTERNAL_MEMORY;
            case MEMORY:
                return ComponentTable.MEMORY;
            default:
                throw new IllegalArgumentException("An invalid category '"+category+"' was specified.");
        }
    }
}
