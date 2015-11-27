package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import org.apache.commons.lang3.StringUtils;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;

import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverServiceImpl implements DriverService {

    private static final Logger LOGGER = Logger.getLogger(DriverServiceImpl.class);

    private EntityManager entityManager;
    private DriverDAO driverDAO;
    private TruckDAO truckDAO;

    public DriverServiceImpl(DriverDAO driverDAO, TruckDAO truckDAO, EntityManager entityManager) {
        this.driverDAO = driverDAO;
        this.entityManager = entityManager;
        this.truckDAO = truckDAO;
    }

    @Override
    public Driver addNewDriver(Driver newDriver) throws LogiwebServiceException {
        validateFormValues(newDriver);

        try {
            newDriver.setDriverStatus(DriverStatus.REST);

            entityManager.getTransaction().begin();
            Driver driverWithSamePersonalNumber = driverDAO.findDriverByPersonalNumber(newDriver.getPersonalNumber());

            if (driverWithSamePersonalNumber != null) {
                throw new LogiwebServiceException("Driver with this personal number #" + newDriver.getPersonalNumber()
                        + " is already exist.");
            }

            driverDAO.create(newDriver);
            entityManager.getTransaction().commit();

            LOGGER.info("Driver created: " + newDriver.getFirstName() + " " + newDriver.getLastName()
                    + " personal number #" + newDriver.getPersonalNumber());

        } catch (LogiwebDAOException e) {
            LOGGER.warn("Exception in DriverServiceImpl - addNewDriver().", e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return newDriver;
    }

    //***
    @Override
    public void editDriver(Driver editedDriver) throws LogiwebServiceException {

    }

    @Override
    public List<Driver> findAllDrivers() throws LogiwebServiceException {
        List<Driver> allDriversResult;

        try {
            entityManager.getTransaction().begin();
            allDriversResult = driverDAO.findAll();
            entityManager.getTransaction().commit();

        } catch (LogiwebDAOException e) {
            LOGGER.warn("Exception in DriverServiceImpl - findAllDrivers().", e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return allDriversResult;
    }

    @Override
    public Driver findDriverById(Integer driverId) throws LogiwebServiceException {
        Driver driverResult;

        try {
            entityManager.getTransaction().begin();
            driverResult = driverDAO.findById(driverId);
            entityManager.getTransaction().commit();

        } catch (LogiwebDAOException e) {
            LOGGER.warn("Exception in DriverServiceImpl - findDriverById().", e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return driverResult;
    }

    @Override
    public void deleteDriver(Driver deletedDriver) throws LogiwebServiceException {
        try {
            entityManager.getTransaction().begin();
            Driver existingDriverToDelete = driverDAO.findById(deletedDriver.getDriverId());

            if (existingDriverToDelete == null) {
                throw new LogiwebServiceException("Driver with driver ID#"
                        + deletedDriver.getDriverId() + " not found.");
            }

            if (existingDriverToDelete.getCurrentTruckFK() != null) {
                throw new LogiwebServiceException("Driver is assigned to truck. Removal is not possible.");
            }

            driverDAO.delete(existingDriverToDelete);
            entityManager.getTransaction().commit();

            LOGGER.info("Driver with personal number #" + existingDriverToDelete.getPersonalNumber()
                    + " " + existingDriverToDelete.getFirstName() + " " + existingDriverToDelete.getLastName() + " removed");

        } catch (LogiwebDAOException e) {
            LOGGER.warn("Exception in DriverServiceImpl - deleteDriver().", e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    private void validateFormValues(Driver driver) throws LogiwebServiceException {
        if(driver.getPersonalNumber() <= 0) {
            throw new LogiwebServiceException("Driver personal number can not be 0 or negative.");
        }
        else if (StringUtils.isBlank(driver.getFirstName())) {
            throw new LogiwebServiceException("Driver first name can not be empty.");
        }
        else if (StringUtils.isBlank(driver.getLastName())) {
            throw new LogiwebServiceException("Driver last name can not be empty.");
        }
        else if (driver.getCurrentCityFK() == null || driver.getCurrentCityFK().getCityId() == 0) {
            throw new LogiwebServiceException("Driver current city is not set.");
        }
    }
}
