package com.murex.retail.repository.hibernate.order;

import com.murex.retail.model.order.Order;
import com.murex.retail.repository.hibernate.HibernateDatabaseUtilities;
import com.murex.retail.repository.hibernate.HibernateRepositoryException;
import com.murex.retail.repository.hibernate.HibernateUtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateOrderRepository {
    private static final Logger LOG = LogManager.getLogger(HibernateOrderRepository.class);
    private static HibernateOrderRepository repository;

    public HibernateOrderRepository() {
    }

    public static HibernateOrderRepository getRepository() {
        if (repository == null) {
            repository = new HibernateOrderRepository();
        }
        return repository;
    }

    public void save(Order order) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        try {
            transaction.begin();

            session.saveOrUpdate(order);
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            session.close();
            transaction.commit();
        } catch (Exception e) {
            String message = "Unable to save order with id: " + order.getId();
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void saveAll(List<Order> orders) throws HibernateRepositoryException {
        for (Order order : orders) {
            this.save(order);
        }
    }

    public Optional<Order> fetchOrder(String id) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();

        try {
            transaction.begin();
            CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
            Root<Order> root = criteria.from(Order.class);
            criteria.select(root)
                    .where(builder.equal(root.get("id"), id));
            List<Order> orders = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            if (orders.isEmpty()) {
                return Optional.empty();
            }
            session.close();
            return Optional.of(orders.get(0));
        } catch (Exception e) {
            String message = "Unable to fetch order with id: " + id;
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public List<Order> fetchAll() throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        try {
            transaction.begin();
            CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
            criteria.from(Order.class);
            List<Order> orders = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            session.close();
            return orders;
        } catch (Exception e) {
            String message = "Unable to fetch orders from database.";
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void update(Order order) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        try {
            transaction.begin();

            session.update(order);
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            session.close();
            transaction.commit();
        } catch (Exception e) {
            String message = "Unable to update order with id: " + order.getId();
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}

