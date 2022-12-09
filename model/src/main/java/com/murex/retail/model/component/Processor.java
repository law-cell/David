package com.murex.retail.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "PROCESSOR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Processor extends ComputerComponent {
    public Processor() {
    }

    @Column(name = "productLine")
    private String productLine;
    @Column(name = "numberOfCores")
    private String numberOfCores;
    @Column(name = "processorClockSpeed")
    private String processorClockSpeed;
    @Column(name = "graphicClockSpeed")
    private String graphicClockSpeed;

    Processor(ComponentBuilder builder) {
        super(builder);
        this.productLine = builder.productLine;
        this.numberOfCores = builder.numberOfCores;
        this.processorClockSpeed = builder.processorClockSpeed;
        this.graphicClockSpeed = builder.graphicClockSpeed;
    }

    public String getProductLine() {
        return productLine;
    }

    public String getNumberOfCores() {
        return numberOfCores;
    }

    public String getProcessorClockSpeed() {
        return processorClockSpeed;
    }

    public String getGraphicsClockSpeed() {
        return graphicClockSpeed;
    }

    public boolean equals(ComputerComponent component) {
        if (super.equals(component) && component instanceof Processor) {
            Processor processor = (Processor) component;
            return this.productLine.equals(processor.getProductLine()) &&
                    this.getNumberOfCores().equals(processor.getNumberOfCores()) &&
                    this.getProcessorClockSpeed().equals(processor.getProcessorClockSpeed()) &&
                    this.getGraphicsClockSpeed().equals(processor.getGraphicsClockSpeed());
        }
        return false;
    }
}