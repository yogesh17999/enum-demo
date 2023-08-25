package com.example.enumdemo.exceptionHandler;

import com.example.enumdemo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ApiResponse> getException(Exception ex)
    {
        return new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.EXPECTATION_FAILED.value()).message(ex.getMessage()).build(),HttpStatus.EXPECTATION_FAILED);
    }

}
