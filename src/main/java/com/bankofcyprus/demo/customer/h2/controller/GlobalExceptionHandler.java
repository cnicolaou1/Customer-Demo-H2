package com.bankofcyprus.demo.customer.h2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankofcyprus.demo.customer.h2.api.dto.ErrorResponse;
import com.bankofcyprus.demo.customer.h2.exception.CustomerDemoException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse
    handleException(Exception ex) {
        LOG.error(ex.getMessage(), ex);
        return new ErrorResponse( "999", "There was an Error!");
    }

    @ExceptionHandler(value = CustomerDemoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(CustomerDemoException ex) {
        LOG.error(ex.getMessage(), ex);
        return new ErrorResponse( ex.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuffer messageSB = new StringBuffer();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if(messageSB.length()>0)
                messageSB.append(", ");

            messageSB.append(((FieldError) error).getField()+": "+error.getDefaultMessage());
        });

        LOG.error(messageSB.toString(), ex);
        return new ErrorResponse("200", messageSB.toString());
    }
}
