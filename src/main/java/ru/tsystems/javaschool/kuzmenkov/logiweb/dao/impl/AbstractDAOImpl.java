package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.AbstractDAO;

import javax.persistence.EntityManager;
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
    public T create(T entity) {
        try {
            entityManager.persist(entity);

        } catch (Exception e) {
            System.out.println("Exception in AbstractDAOImpl.");
        }

        return entity;
    }

    @Override
    public T findById(Integer id) {
        return null;
    }

    @Override
    public void update(T changeableEntity) {

    }

    @Override
    public void delete(T removedEntity) {

    }

    @Override
    public List<T> findAll() {
        List<T> queryResult = null;

        try {
            queryResult = getEntityManager().createQuery(
                    "SELECT t FROM " + getEntityClass().getSimpleName() + " t")
                    .getResultList();

        } catch (Exception e) {
            System.out.println("Exception in AbstractDAOImpl");;

        }

        return queryResult;
    }
}
