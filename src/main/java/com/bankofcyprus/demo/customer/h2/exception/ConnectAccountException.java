package com.bankofcyprus.demo.customer.h2.exception;

public class ConnectAccountException extends CustomerDemoException{
	private static final long serialVersionUID = 789147845877431008L;
	
	private static final String CODE = "101";

    public ConnectAccountException() {
        super();
    }

    public ConnectAccountException(String message) {
        super(message,CODE);
    }

    public ConnectAccountException(String message, Throwable cause) {
        super(message, cause, CODE);
    }
}
