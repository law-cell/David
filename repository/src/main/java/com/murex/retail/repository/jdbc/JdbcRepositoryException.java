package com.murex.retail.repository.jdbc;

import com.murex.retail.repository.exceptions.RepositoryException;

public class JdbcRepositoryException extends RepositoryException {
    public JdbcRepositoryException(String message, Exception e) {
        super(message, e);
    }
}
