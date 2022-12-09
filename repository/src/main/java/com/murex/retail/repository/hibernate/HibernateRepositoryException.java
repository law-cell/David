package com.murex.retail.repository.hibernate;

import com.murex.retail.repository.exceptions.RepositoryException;

public class HibernateRepositoryException extends RepositoryException {
    public HibernateRepositoryException(String message, Exception e) {
        super(message, e);
    }
}

