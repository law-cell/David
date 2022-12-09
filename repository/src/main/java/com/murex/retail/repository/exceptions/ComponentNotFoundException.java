package com.murex.retail.repository.exceptions;

public class ComponentNotFoundException extends Exception {
    public ComponentNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
