package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.CityDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.CityService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Nikolay on 26.11.2015.
 */
public class CityServiceImpl implements CityService {

    private EntityManager entityManager;

    private CityDAO cityDAO;

    public CityServiceImpl(EntityManager entityManager, CityDAO cityDAO) {
        this.entityManager = entityManager;
        this.cityDAO = cityDAO;
    }

    @Override
    public List<City> findAllCities() throws LogiwebServiceException {
        List<City> allCitiesResult;

        try {
            entityManager.getTransaction().begin();
            allCitiesResult = cityDAO.findAll();
            entityManager.getTransaction().commit();

        } catch (LogiwebDAOException e) {
            //LOG.warn("Something unexcpected happend.");
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return allCitiesResult;
    }
}
