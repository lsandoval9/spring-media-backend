package com.lsandoval9.springmedia.dto.exceptionHandler;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MimetypeNotSupportedDto {

    @NotNull

    private String error;

    @Size(min = 5, max = 30, message = "Please provide a valid error message")
    private String message;

    private LocalDateTime date;

    public MimetypeNotSupportedDto(String error, String message, LocalDateTime date) {
        this.error = error;
        this.message = message;
        this.date = date;
    }
}
