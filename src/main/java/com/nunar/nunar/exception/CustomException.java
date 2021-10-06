package com.nunar.nunar.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CustomException extends Exception {

    private final HttpStatus code;
    private final String message;

}
