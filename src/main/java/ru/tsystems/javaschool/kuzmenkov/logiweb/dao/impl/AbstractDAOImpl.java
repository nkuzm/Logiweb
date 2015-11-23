package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.AbstractDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class AbstractDAOImpl<T> implements AbstractDAO<T> {

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
            entityManager.persist(newEntity);

        } catch (Exception e) {
            System.out.println("Exception in AbstractDAOImpl.");
            throw new LogiwebDAOException(e);
        }

        return newEntity;
    }

    @Override
    public T findById(Integer id) throws LogiwebDAOException {
        return null;
    }

    @Override
    public void update(T changeableEntity) throws LogiwebDAOException {

    }

    @Override
    public void delete(T removedEntity) throws LogiwebDAOException {

    }

    @Override
    public List<T> findAll() throws LogiwebDAOException {
        List<T> allEntitiesResult;

        try {
            allEntitiesResult = getEntityManager().createQuery("SELECT t FROM "
                    + getEntityClass().getSimpleName() + " t").getResultList();

        } catch (Exception e) {
            System.out.println("Exception in AbstractDAOImpl");;
            throw new LogiwebDAOException(e);
        }

        return new ArrayList<T>(allEntitiesResult);
    }
}
