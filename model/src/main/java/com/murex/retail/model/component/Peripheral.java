package com.murex.retail.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "PERIPHERAL")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Peripheral extends ComputerComponent {
    public Peripheral() {}
    @Column
    private String color;
    @Column
    private String dimension;

    Peripheral(ComponentBuilder builder) {
        super(builder);
        this.color = builder.color;
        this.dimension = builder.dimension;
    }

    public String getColor() {
        return color;
    }

    public String getDimension() {
        return dimension;
    }

    public boolean equals(ComputerComponent component) {
        if (super.equals(component) && component instanceof Peripheral) {
            Peripheral peripheral = (Peripheral) component;
            return this.color.equals(peripheral.getColor()) &&
                    this.dimension.equals(peripheral.getDimension());
        }
        return false;
    }
}