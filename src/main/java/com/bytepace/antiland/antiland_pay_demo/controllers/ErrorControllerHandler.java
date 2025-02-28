package com.bytepace.antiland.antiland_pay_demo.controllers;

import com.bytepace.antiland.antiland_pay_demo.models.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PaymentController.class)
public class ErrorControllerHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorDto handlerPaymentException(Exception e) {
        return ErrorDto.builder()
                .status(400)
                .message(e.getMessage())
                .build();
    }
}
