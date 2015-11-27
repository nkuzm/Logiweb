package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface AbstractDAO<T> {

    T create(T newEntity) throws LogiwebDAOException;

    T findById(Integer entityId) throws LogiwebDAOException;

    void update(T changeableEntity) throws LogiwebDAOException;

    void delete(T deletedEntity) throws LogiwebDAOException;

    List<T> findAll() throws LogiwebDAOException;
}
