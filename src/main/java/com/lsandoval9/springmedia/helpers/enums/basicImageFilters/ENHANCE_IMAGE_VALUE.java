package com.lsandoval9.springmedia.helpers.enums.basicImageFilters;

public enum ENHANCE_IMAGE_VALUE {

    LIGHT(1.2),
    MEDIUM(1.5),
    HIGHT(2.0);

    private final double value;

    ENHANCE_IMAGE_VALUE(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getPercent() {

        return this.value / 100;

    }
}
