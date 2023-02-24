package com.bankofcyprus.demo.customer.h2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerCreateException extends CustomerDemoException{
	private static final long serialVersionUID = 22724623868186156L;
	
	private static final String CODE = "102";

    public CustomerCreateException() {
        super();
    }

    public CustomerCreateException(String message) {
        super(message,CODE);
    }

    public CustomerCreateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }
}
