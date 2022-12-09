package com.murex.retail.model.component;

public class ComponentBuilder {
    String id;
    Category category;
    String name;
    String brand;
    String productLine;
    String numberOfCores;
    String processorClockSpeed;
    String graphicClockSpeed;
    String dimension;
    String resolution;
    String color;
    String connectionInterface;
    String size;
    double price;
    int quantity;

    public ComputerComponent build() throws IllegalArgumentException {
        switch (this.category) {
            case CPU:
            case GPU:
                return new Processor(this);
            case MONITOR:
                return new Monitor(this);
            case MOUSE:
            case KEYBOARD:
                return new Peripheral(this);
            case STORAGE:
                return new ExternalMemory(this);
            case MEMORY:
                return new Memory(this);
            default:
                throw new IllegalArgumentException("An invalid category was specified.");
        }
    }

    public ComponentBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ComponentBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public ComponentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ComponentBuilder brand(String brand) {
        this.brand = brand;
        return this;
    }

    public ComponentBuilder productLine(String productLine) {
        this.productLine = productLine;
        return this;
    }

    public ComponentBuilder numberOfCores(String numberOfCores) {
        this.numberOfCores = numberOfCores;
        return this;
    }

    public ComponentBuilder processorClockSpeed(String processorClockSpeed) {
        this.processorClockSpeed = processorClockSpeed;
        return this;
    }

    public ComponentBuilder graphicClockSpeed(String graphicClockSpeed) {
        this.graphicClockSpeed = graphicClockSpeed;
        return this;
    }

    public ComponentBuilder dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public ComponentBuilder resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public ComponentBuilder color(String color) {
        this.color = color;
        return this;
    }

    public ComponentBuilder connectionInterface(String connectionInterface) {
        this.connectionInterface = connectionInterface;
        return this;
    }

    public ComponentBuilder size(String size) {
        this.size = size;
        return this;
    }

    public ComponentBuilder price(double price) {
        this.price = price;
        return this;
    }

    public ComponentBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}