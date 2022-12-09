package com.murex.retail.repository.hibernate;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.DatabaseTerminator;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.hibernate.order.HibernateOrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateDatabaseTerminator implements DatabaseTerminator {
    private static final Logger LOG = LogManager.getLogger(HibernateDatabaseTerminator.class);
    private final HibernateOrderRepository repository;

    public HibernateDatabaseTerminator() {
        this.repository = new HibernateOrderRepository();
    }

    @Override
    public void terminate(ComponentTable[] componentTables) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        EntityTransaction transaction = entityManager.getTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        try {
            transaction.begin();
            CriteriaDelete<ComputerComponent> criteria = builder.createCriteriaDelete(ComputerComponent.class);
            Root<ComputerComponent> root = criteria.from(ComputerComponent.class);
            session.createQuery(criteria)
                    .executeUpdate();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            LOG.info("ALL records deleted.");
        } catch (HibernateException e) {
            LOG.error("Could not delete records in database.");
        } finally {
            session.close();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void terminateOrder() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        EntityTransaction transaction = entityManager.getTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        try {
            transaction.begin();
            List<Order> orders = repository.fetchAll();
            orders.forEach(o->session.remove(entityManager.contains(o) ? o : entityManager.merge(o)));
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            LOG.info("ALL records deleted.");
            session.close();
        } catch (HibernateException e) {
            LOG.error("Could not delete records in database.");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
