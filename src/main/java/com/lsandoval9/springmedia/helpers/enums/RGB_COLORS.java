package com.lsandoval9.springmedia.helpers.enums;

public enum RGB_COLORS {

    RED("red"),
    BLUE("blue"),
    GREEN("green");

    private final String color;

    RGB_COLORS(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
