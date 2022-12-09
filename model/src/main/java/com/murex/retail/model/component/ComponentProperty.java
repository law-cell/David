package com.murex.retail.model.component;

public enum ComponentProperty {
        ID(0),
        CATEGORY(1),
        NAME(2),
        BRAND(3),
        PRODUCT_LINE(4),
        NUMBER_OF_CORES(5),
        PROCESSOR_CLOCK_SPEED(6),
        GRAPHIC_CLOCK_SPEED(7),
        DIMENSION(8),
        RESOLUTION(9),
        COLOR(10),
        INTERFACE(11),
        SIZE(12),
        PRICE(13),
        QUANTITY(14);

        private final int index;

        ComponentProperty(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
}
