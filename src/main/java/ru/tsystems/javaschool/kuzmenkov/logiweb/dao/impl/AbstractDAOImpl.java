package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.AbstractDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class with set of basic CRUD operations.
 *
 * @author Nikolay Kuzmenkov.
 */
public class AbstractDAOImpl<T> implements AbstractDAO<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractDAOImpl.class);

    private Class<T> entityClass;
    private EntityManager entityManager;

    public AbstractDAOImpl(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }

    protected final Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T create(T newEntity) throws LogiwebDAOException {
        try {
            getEntityManager().persist(newEntity);

        } catch (Exception e) {
            LOGGER.warn("Exception in AbstractDAOImpl - create().", e);
            throw new LogiwebDAOException(e);
        }

        return newEntity;
    }

    @Override
    public T findById(Integer entityId) throws LogiwebDAOException {
        T entityResult;

        try {
            entityResult = getEntityManager().find(getEntityClass(), entityId);

        } catch (Exception e) {
            LOGGER.warn("Exception in AbstractDAOImpl - findById().", e);
            throw new LogiwebDAOException(e);
        }

        return entityResult;
    }

    @Override
    public void update(T changeableEntity) throws LogiwebDAOException {
        if (changeableEntity == null) {
            return;
        }

        try {
            getEntityManager().merge(changeableEntity);

        } catch (Exception e) {
            System.out.println("Exception in AbstractDAOImpl.");
            throw new LogiwebDAOException(e);
        }
    }

    @Override
    public void delete(T deletedEntity) throws LogiwebDAOException {
        try {
            getEntityManager().remove(deletedEntity);

        } catch (Exception e) {
            LOGGER.warn("Exception in AbstractDAOImpl - delete().", e);
            throw new LogiwebDAOException(e);
        }
    }

    @Override
    public List<T> findAll() throws LogiwebDAOException {
        List<T> allEntitiesResult;

        try {
            allEntitiesResult = getEntityManager().createQuery("SELECT t FROM "
                    + getEntityClass().getSimpleName() + " t").getResultList();

        } catch (Exception e) {
            LOGGER.warn("Exception in AbstractDAOImpl - findAll().", e);
            throw new LogiwebDAOException(e);
        }

        return new ArrayList<T>(allEntitiesResult);
    }
}
