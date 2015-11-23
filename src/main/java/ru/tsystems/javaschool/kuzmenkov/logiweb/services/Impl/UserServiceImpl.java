package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.UserDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.UserService;

import javax.persistence.EntityManager;

/**
 * Created by Nikolay on 22.11.2015.
 */
public class UserServiceImpl implements UserService {

    private EntityManager entityManager;
    private UserDAO userDAO;

    public UserServiceImpl(EntityManager entityManager, UserDAO userDAO) {
        this.entityManager = entityManager;
        this.userDAO = userDAO;
    }
}
