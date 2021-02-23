package com.lsandoval9.springmedia.dto.exceptionHandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessageDto implements Serializable {


    private String error;

    private String message;

    private LocalDateTime date;

    public ErrorMessageDto(String error, String message, LocalDateTime date) {
        this.error = error;
        this.message = message;
        this.date = date;
    }


}
