package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverDAOImpl extends AbstractDAOImpl<Driver> implements DriverDAO {

    public DriverDAOImpl(Class<Driver> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Override
    public Driver findDriverByPersonalNumber(Integer driverPersonalNumber) {
        Driver queryResult = null;

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery("SELECT dr FROM Driver dr " +
                    "WHERE dr.personalNumber = :driverPersonalNumber", Driver.class);
            query.setParameter("driverPersonalNumber", driverPersonalNumber);
            List<Driver> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                queryResult = resultList.get(0);
            }

        } catch (Exception e) {
            System.out.println("Exception in DriverDAOImpl.");
        }

        return queryResult;
    }
}
