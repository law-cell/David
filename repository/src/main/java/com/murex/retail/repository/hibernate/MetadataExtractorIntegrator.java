package com.murex.retail.repository.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;


public class MetadataExtractorIntegrator implements Integrator {
    public static final MetadataExtractorIntegrator INSTANCE =
            new MetadataExtractorIntegrator();
    private Metadata metadata;

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        this.metadata = metadata;
    }

    @Override
    public void disintegrate(
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
    }
}