package com.lsandoval9.springmedia.exceptions;

import java.util.List;

public class InvalidFileException extends RuntimeException{

    private List<String> types;

    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException(String message, List<String> types) {
        super(message);
        this.types = types;
    }

    public List<String> getTypes() {

        return this.types;
    }
}
