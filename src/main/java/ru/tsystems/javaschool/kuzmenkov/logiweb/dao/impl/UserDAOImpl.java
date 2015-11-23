package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.UserDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.User;

import javax.persistence.EntityManager;

/**
 * Created by Nikolay on 22.11.2015.
 */
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {

    public UserDAOImpl(Class<User> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }
}
