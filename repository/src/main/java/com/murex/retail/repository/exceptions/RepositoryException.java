package com.murex.retail.repository.exceptions;

public class RepositoryException extends Exception{
    public RepositoryException(String message, Exception e) {
        super(message, e);
    }
}
