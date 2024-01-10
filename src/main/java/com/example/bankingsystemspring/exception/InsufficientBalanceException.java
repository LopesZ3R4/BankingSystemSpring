package com.example.bankingsystemspring.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super("Not enough balance");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
