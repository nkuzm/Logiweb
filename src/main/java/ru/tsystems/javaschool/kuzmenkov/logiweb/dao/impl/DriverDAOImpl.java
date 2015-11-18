package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverDAOImpl extends AbstractDAOImpl<Driver> implements DriverDAO {

    public DriverDAOImpl(Class<Driver> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Override
    public void addDriver(Driver newDriver, Integer personalNumber, EntityManager entityManager) throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            //logger.info("Add new driver with personal number: " + personalNumber);

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
