package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverDAOImpl implements DriverDAO {

    private Logger logger = Logger.getLogger(DriverDAOImpl.class.getName());


    @Override
    public void addDriver(Driver newDriver, Integer personalNumber, EntityManager entityManager) throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            logger.info("Add new driver with personal number: " + personalNumber);

            List<Integer> ids = checkUniquePersonalNumber(entityManager);

            if (ids.contains(personalNumber)) {
                throw new IllegalArgumentException("Driver with this personal number is already exists.");
            }

            entityManager.merge(newDriver);
            transaction.commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private List<Integer> checkUniquePersonalNumber(EntityManager entityManager) throws SQLException {
        return entityManager.createQuery("SELECT d.personalNumber FROM Driver d").getResultList();
    }
}
