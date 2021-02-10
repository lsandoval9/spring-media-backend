package com.lsandoval9.springmedia.helpers.enums.basicImageFilters;

public enum UNICOLOR_FILTER_VALUES {

    LIGHT(15),
    MEDIUM(30),
    HIGHT(45);

    private final int value;

    UNICOLOR_FILTER_VALUES(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
