package com.bankofcyprus.demo.customer.h2.exception;

import lombok.Data;

@Data
public class CustomerDemoException extends RuntimeException{
	private static final long serialVersionUID = 6063393230296901283L;
	
	private String code = "100";

    public CustomerDemoException() {
    }

    public CustomerDemoException(String message) {
        super(message);
    }

    public CustomerDemoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerDemoException(String message, String code){
        super(message);
        this.code = code;
    }

    public CustomerDemoException(String message, Throwable cause, String code){
        super(message, cause);
        this.code = code;
    }
}
