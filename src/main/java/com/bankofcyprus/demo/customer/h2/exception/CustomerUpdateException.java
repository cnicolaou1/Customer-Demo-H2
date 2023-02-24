package com.bankofcyprus.demo.customer.h2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerUpdateException extends CustomerDemoException{
	private static final long serialVersionUID = 6611982406262491149L;
	
	private static final String CODE = "103";

    public CustomerUpdateException() {
        super();
    }

    public CustomerUpdateException(String message) {
        super(message,CODE);
    }

    public CustomerUpdateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }
}
