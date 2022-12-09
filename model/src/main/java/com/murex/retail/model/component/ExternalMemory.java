package com.murex.retail.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="EXTERNAL_MEMORY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ExternalMemory extends Memory {
    public ExternalMemory() {}
    @Column
    private String dimension;

    ExternalMemory(ComponentBuilder builder) {
        super(builder);
        this.dimension = builder.dimension;
    }

    public String getDimension() {
        return dimension;
    }

    public boolean equals(ComputerComponent component) {
        if (super.equals(component) && component instanceof ExternalMemory) {
            ExternalMemory memory = (ExternalMemory) component;
            return this.dimension.equals(memory.getDimension());
        }
        return false;
    }
}