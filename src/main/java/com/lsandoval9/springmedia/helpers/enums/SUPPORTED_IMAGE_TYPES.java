package com.lsandoval9.springmedia.helpers.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum SUPPORTED_IMAGE_TYPES {

    PNG("image/png"),
    WEBMP("image/webp"),
    JPEG("image/jpeg");

    private final String type;

    SUPPORTED_IMAGE_TYPES(String type) {

        this.type = type;

    }

    public String getTypeName() {
        return this.type;
    }

    @Override
    public String toString() {
        return type + ",";
    }

}
