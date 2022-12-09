package com.murex.retail.repository.hibernate.inventory;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.repository.DatabaseRepository;
import com.murex.retail.repository.exceptions.RepositoryException;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateInventoryRepository implements DatabaseRepository {
    private static final Logger LOG = LogManager.getLogger(HibernateInventoryRepository.class);
    private static HibernateInventoryRepository repository;

    public HibernateInventoryRepository() {
    }

    public static HibernateInventoryRepository getRepository() {
        if (repository == null) {
            repository = new HibernateInventoryRepository();
        }

        return repository;
    }

    @Override
    public void save(ComputerComponent computerComponent) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        try {
            transaction.begin();

            session.saveOrUpdate(computerComponent);
            HibernateDatabaseUtilities.flushAndClear(entityManager);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            String message = "Unable to save component with id: " + computerComponent.getId();
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveAll(List<ComputerComponent> computerComponents) throws HibernateRepositoryException {
        for (ComputerComponent computerComponent : computerComponents) {
            this.save(computerComponent);
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();

        try {
            transaction.begin();
            CriteriaQuery<ComputerComponent> criteria = builder.createQuery(ComputerComponent.class);
            Root<ComputerComponent> root = criteria.from(ComputerComponent.class);
            criteria.select(root)
                    .where(builder.equal(root.get("id"), id));
            List<ComputerComponent> computerComponents = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            session.close();
            if (computerComponents.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(computerComponents.get(0));
        } catch (Exception e) {
            String message = "Unable to fetch component with id: " + id;
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponent(String id, Category category) throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();

        try {
            transaction.begin();
            CriteriaQuery<ComputerComponent> criteria = builder.createQuery(ComputerComponent.class);
            Root<ComputerComponent> root = criteria.from(ComputerComponent.class);
            Predicate[] predicates = new Predicate[]{
                    builder.equal(root.get("id"), id),
                    builder.equal(root.get("category"), category)
            };
            criteria.select(root)
                    .where(predicates);
            List<ComputerComponent> computerComponents = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            session.close();
            if (computerComponents.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(computerComponents.get(0));
        } catch (Exception e) {
            String message = "Unable to fetch component with id: " + id + " and Category: " + category;
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<ComputerComponent> fetchAll() throws HibernateRepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        try {
            transaction.begin();
            CriteriaQuery<ComputerComponent> criteria = builder.createQuery(ComputerComponent.class);
            criteria.from(ComputerComponent.class);
            List<ComputerComponent> computerComponents = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            session.close();
            return computerComponents;
        } catch (Exception e) {
            String message = "Unable to fetch components from tables in database.";
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<ComputerComponent> fetchComponentByName(String name) throws RepositoryException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("model", HibernateUtilityClass.getProperties());
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        try {
            transaction.begin();
            CriteriaQuery<ComputerComponent> criteria = builder.createQuery(ComputerComponent.class);
            Root<ComputerComponent> root = criteria.from(ComputerComponent.class);
            criteria.select(root)
                    .where(builder.equal(root.get("name"), name));
            List<ComputerComponent> computerComponents = session.createQuery(criteria)
                    .getResultList();
            HibernateDatabaseUtilities.flushAndClear(entityManager);
            transaction.commit();
            session.close();
            if (computerComponents.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(computerComponents.get(0));
        } catch (Exception e) {
            String message = "Unable to fetch component with name: " + name;
            LOG.error(message, e);
            throw new HibernateRepositoryException(message, e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}