package com.murex.retail.repository.inmemory;

import com.murex.retail.repository.exceptions.RepositoryException;

public class InMemoryRepositoryException extends RepositoryException {
    public InMemoryRepositoryException(String message, Exception e) {
        super(message, e);
    }
}

