package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface AbstractDAO<T> {

    T create(T newEntity);

    T findById(Integer idOfEntity);

    void update(T changeableEntity);

    void delete(T removedEntity);
}
