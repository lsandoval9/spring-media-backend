package com.lsandoval9.springmedia.helpers.enums;

public enum SupportedImageTypes {

    PNG("image/png"),
    WEBMP("image/webp"),
    JPEG("image/jpeg");

    private final String type;

    SupportedImageTypes(String type) {

        this.type = type;

    }

    public String getTypeName() {
        return type;
    }
}
