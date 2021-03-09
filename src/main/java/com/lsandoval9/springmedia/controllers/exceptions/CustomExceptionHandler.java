package com.lsandoval9.springmedia.controllers.exceptions;

import com.lsandoval9.springmedia.dto.exceptionHandler.ErrorMessageDto;
import com.lsandoval9.springmedia.exceptions.InvalidFileException;
import com.lsandoval9.springmedia.exceptions.MimetypeNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestControllerAdvice()
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MimetypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto mimetypeNotSupportedException(
            MimetypeNotSupportedException e,
            String type) {

        ErrorMessageDto error = new ErrorMessageDto(
                e.getMessage(),
                "Please provide a valid file of type: " + type,
                LocalDateTime.now()
        );

        return error;
    }


    @ExceptionHandler(value = {InvalidFileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto invalidFileException(InvalidFileException e) {

        AtomicReference<String> f = null;

        var message = String.join(",", e.getTypes());



        ErrorMessageDto errorMessage = new ErrorMessageDto(
                e.getMessage(),
                message,
                LocalDateTime.now()
        );


        return errorMessage;
    }


    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto iOException(IOException e) {

        ErrorMessageDto errorMessage = new ErrorMessageDto(

                e.getMessage(),
                "CHECK_MIMETYPE",
                LocalDateTime.now()
        );


        return errorMessage;

    }


    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto fileSizeExceded(IOException e) {

        ErrorMessageDto errorMessage = new ErrorMessageDto(

                e.getMessage(),
                "Please provide a file smaller than 2 MB",
                LocalDateTime.now()
        );


        return errorMessage;

    }

}
