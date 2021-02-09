package com.lsandoval9.springmedia.helpers.enums;

public enum SUPPORTED_IMAGE_TYPES {

    PNG("image/png"),
    WEBMP("image/webp"),
    JPEG("image/jpeg");

    private final String type;

    SUPPORTED_IMAGE_TYPES(String type) {

        this.type = type;

    }

    public String getTypeName() {
        return type;
    }
}
