package com.murex.retail.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="MONITOR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Monitor extends Peripheral {
    public Monitor() {}
    @Column
    private String resolution;
    Monitor(ComponentBuilder builder) {
        super(builder);
        this.resolution = builder.resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public boolean equals(ComputerComponent component) {
        if (super.equals(component) && component instanceof Monitor) {
            Monitor monitor = (Monitor) component;
            return this.resolution.equals(monitor.getResolution());
        }
        return false;
    }
}