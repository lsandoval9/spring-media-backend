package com.lsandoval9.springmedia.helpers.enums.basicImageFilters;

public enum BRIGHTNESS_IMAGE_VALUES {

    LESS_VERY_HIGHT(-50),
    LESS_HIGHT(-35),
    LESS_MEDIUM(-15),
    LESS_LIGHT(-5),
    LIGHT(5),
    MEDIUM(15),
    HIGHT(25),
    VERY_HIGHT(35);

    private final double value;

    BRIGHTNESS_IMAGE_VALUES(double value) {

        this.value = value;

    }

    public double getValue() {
        return value;
    }

    public double getPorcenteage() {

        return this.value / 100;

    }
}
