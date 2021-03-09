package com.lsandoval9.springmedia.exceptions;

import com.lsandoval9.springmedia.helpers.enums.SUPPORTED_IMAGE_TYPES;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MimetypeNotSupportedException extends  RuntimeException{

    private final List<String> types;

    public MimetypeNotSupportedException(String message) {
        super(message);

        this.types = Arrays.stream(SUPPORTED_IMAGE_TYPES.values())
        .map(SUPPORTED_IMAGE_TYPES::getTypeName)
                .collect(Collectors.toList());
    }

    public List<String> getTypes() {
        return types;
    }
}
