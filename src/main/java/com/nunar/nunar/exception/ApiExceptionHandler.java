package com.nunar.nunar.exception;

import com.nunar.nunar.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public BaseResponse<Void> handleAllException(CustomException exception) {
        return new BaseResponse<>(exception.getCode().value(), exception.getMessage() , null);
    }

}
