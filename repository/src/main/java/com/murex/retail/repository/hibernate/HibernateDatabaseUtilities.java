package com.murex.retail.repository.hibernate;

import javax.persistence.EntityManager;

public class HibernateDatabaseUtilities {
    private HibernateDatabaseUtilities() {}

    public static void flushAndClear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }
}
