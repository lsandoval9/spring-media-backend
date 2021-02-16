package com.lsandoval9.springmedia.controllers.exceptions;

import com.lsandoval9.springmedia.dto.exceptionHandler.MimetypeNotSupportedDto;
import com.lsandoval9.springmedia.exceptions.MimetypeNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice()
public class CustomExceptionHandler {

    @ExceptionHandler(value = {MimetypeNotSupportedException.class})
    public ResponseEntity<MimetypeNotSupportedDto> mimetypeNotSupportedException(
            MimetypeNotSupportedException e,
            String type) {

        MimetypeNotSupportedDto error = new MimetypeNotSupportedDto(
                e.getMessage(),
                "Please provide a valid file of type: " + type,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
