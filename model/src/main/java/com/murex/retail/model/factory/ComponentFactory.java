package com.murex.retail.model.factory;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComponentBuilder;
import com.murex.retail.model.component.ComputerComponent;

import static com.murex.retail.model.component.ComponentProperty.BRAND;
import static com.murex.retail.model.component.ComponentProperty.CATEGORY;
import static com.murex.retail.model.component.ComponentProperty.COLOR;
import static com.murex.retail.model.component.ComponentProperty.DIMENSION;
import static com.murex.retail.model.component.ComponentProperty.GRAPHIC_CLOCK_SPEED;
import static com.murex.retail.model.component.ComponentProperty.ID;
import static com.murex.retail.model.component.ComponentProperty.INTERFACE;
import static com.murex.retail.model.component.ComponentProperty.NAME;
import static com.murex.retail.model.component.ComponentProperty.NUMBER_OF_CORES;
import static com.murex.retail.model.component.ComponentProperty.PRICE;
import static com.murex.retail.model.component.ComponentProperty.PROCESSOR_CLOCK_SPEED;
import static com.murex.retail.model.component.ComponentProperty.PRODUCT_LINE;
import static com.murex.retail.model.component.ComponentProperty.QUANTITY;
import static com.murex.retail.model.component.ComponentProperty.RESOLUTION;
import static com.murex.retail.model.component.ComponentProperty.SIZE;

public class ComponentFactory {
    public static ComputerComponent getComponent(String[] componentAttributes) {
        return new ComponentBuilder().id(componentAttributes[ID.getIndex()])
                .category(Category.valueOf(componentAttributes[CATEGORY.getIndex()].toUpperCase()))
                .name(componentAttributes[NAME.getIndex()])
                .brand(componentAttributes[BRAND.getIndex()])
                .price(Double.parseDouble(componentAttributes[PRICE.getIndex()]))
                .quantity(Integer.parseInt(componentAttributes[QUANTITY.getIndex()]))
                .dimension(componentAttributes[DIMENSION.getIndex()])
                .productLine(componentAttributes[PRODUCT_LINE.getIndex()])
                .processorClockSpeed(componentAttributes[PROCESSOR_CLOCK_SPEED.getIndex()])
                .graphicClockSpeed(componentAttributes[GRAPHIC_CLOCK_SPEED.getIndex()])
                .numberOfCores(componentAttributes[NUMBER_OF_CORES.getIndex()])
                .color(componentAttributes[COLOR.getIndex()])
                .resolution(componentAttributes[RESOLUTION.getIndex()])
                .size(componentAttributes[SIZE.getIndex()])
                .connectionInterface(componentAttributes[INTERFACE.getIndex()])
                .build();
    }
}