package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

import java.util.List;

/**
 * DAO interface for basic CRUD operations.
 *
 * @author Nikolay Kuzmenkov.
 * @param <T> persistent object.
 */
public interface AbstractDAO<T> {

    /**
     * Persist new object into database.
     *
     * @param newEntity
     * @return same persistent object.
     * @throws LogiwebDAOException if failed to persist object.
     */
    T create(T newEntity) throws LogiwebDAOException;

    T findById(Integer entityId) throws LogiwebDAOException;

    void update(T changeableEntity) throws LogiwebDAOException;

    void delete(T deletedEntity) throws LogiwebDAOException;

    List<T> findAll() throws LogiwebDAOException;
}
