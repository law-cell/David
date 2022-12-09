package com.murex.retail.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "MEMORY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Memory extends ComputerComponent {
    public Memory() {
    }

    @Column
    private String connectionInterface;
    @Column
    private String size;

    public Memory(ComponentBuilder builder) {
        super(builder);
        this.connectionInterface = builder.connectionInterface;
        this.size = builder.size;
    }

    public String getInterface() {
        return connectionInterface;
    }

    public String getSize() {
        return size;
    }

    public boolean equals(ComputerComponent component) {
        if (super.equals(component) && component instanceof Memory) {
            Memory memory = (Memory) component;
            return this.connectionInterface.equals(memory.getInterface()) &&
                    this.size.equals(memory.getSize());
        }
        return false;
    }
}