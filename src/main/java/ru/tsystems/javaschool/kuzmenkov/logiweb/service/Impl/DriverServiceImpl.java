package ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.DriverService;

import javax.persistence.EntityManager;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverServiceImpl implements DriverService {

    private EntityManager entityManager;

    private DriverDAO driverDAO;

    public DriverServiceImpl(DriverDAO driverDAO, EntityManager entityManager) {
        this.driverDAO = driverDAO;
        this.entityManager = entityManager;
    }

    @Override
    public void addDriver(Driver newDriver) {

    }
}
