package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.AbstractDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

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

    @Override
    public T create(T entity) {


        return null;
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
}
