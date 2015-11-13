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
public class AbstractDAOImpl implements AbstractDAO {

    private DriverDAO driverDAO = new DriverDAOImpl();

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("logiwebPU");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void addDriver(Driver newDriver, Integer personalNumber) throws SQLException {
        driverDAO.addDriver(newDriver, personalNumber, entityManager);
    }

    @Override
    public void closeConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
