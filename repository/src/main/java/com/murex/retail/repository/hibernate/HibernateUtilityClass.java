package com.murex.retail.repository.hibernate;

import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.Collections;
import java.util.Properties;

public class HibernateUtilityClass {
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.integrator_provider",
                (IntegratorProvider) () -> Collections.singletonList(MetadataExtractorIntegrator.INSTANCE));
        return properties;
    }
}