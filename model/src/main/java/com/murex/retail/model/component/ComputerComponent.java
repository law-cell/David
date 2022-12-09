package com.murex.retail.model.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="COMPUTER_COMPONENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "category")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Processor.class, name = "CPU"),
        @JsonSubTypes.Type(value = Processor.class, name = "GPU"),
        @JsonSubTypes.Type(value = Peripheral.class, name = "Keyboard"),
        @JsonSubTypes.Type(value = Peripheral.class, name = "Mouse"),
        @JsonSubTypes.Type(value = Monitor.class, name = "Monitor"),
        @JsonSubTypes.Type(value = Memory.class, name = "Memory"),
        @JsonSubTypes.Type(value = ExternalMemory.class, name = "Storage")
})
public abstract class ComputerComponent {
    public ComputerComponent() {
    }

    private static final String COLUMN_SEPARATOR = "\t|\t";
    @Id
    @Column(name = "id")
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int quantity;


    ComputerComponent(ComponentBuilder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.name = builder.name;
        this.brand = builder.brand;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public String getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decrementQuantity(int n) { //pass here the decrement quantity
        this.quantity -=n;
    }

    @Override
    public boolean equals(Object obj) {
        ComputerComponent component = obj instanceof ComputerComponent ? (ComputerComponent) obj : null;
        return component != null &&
                this.id.equals(component.getId()) &&
                this.category.equals(component.getCategory()) &&
                this.name.equals(component.getName()) &&
                this.brand.equals(component.getBrand()) &&
                this.price == component.getPrice() &&
                this.quantity == component.getQuantity();
    }

    @Override
    public String toString() {
        StringBuilder componentPrint = new StringBuilder();
        componentPrint.append("|\t").append(this.getId()).append(COLUMN_SEPARATOR);
        componentPrint.append(this.getCategory().toString()).append(COLUMN_SEPARATOR);
        componentPrint.append(this.getName()).append(COLUMN_SEPARATOR);
        componentPrint.append(this.getBrand()).append(COLUMN_SEPARATOR);
        componentPrint.append(this.getPrice()).append(COLUMN_SEPARATOR);
        componentPrint.append(this.getQuantity()).append("\t|");

        return componentPrint.toString();
    }
}