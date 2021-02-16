package com.lsandoval9.springmedia.exceptions;

import java.util.List;

public class MimetypeNotSupportedException extends  RuntimeException{

    public MimetypeNotSupportedException(String message, List<String> types) {
        super(message);
    }
}
