package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.UserDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.User;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
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

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws LogiwebServiceException {
        User user;

        try {
            entityManager.getTransaction().begin();
            user = userDAO.getUserByEmailAndPassword(email, password);
            entityManager.getTransaction().commit();

        } catch (LogiwebDAOException e) {
            System.out.println("Exception in UserServiceImpl");
            throw new LogiwebServiceException(e);

        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return user;
    }
}
