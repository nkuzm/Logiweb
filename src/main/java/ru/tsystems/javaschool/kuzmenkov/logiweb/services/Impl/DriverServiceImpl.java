package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverShiftDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.DriverShift;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.DateUtil;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebValidator;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverServiceImpl implements DriverService {

    private static final Logger LOGGER = Logger.getLogger(DriverServiceImpl.class);

    private EntityManager entityManager;
    private DriverDAO driverDAO;
    private TruckDAO truckDAO;
    private DriverShiftDAO driverShiftDAO;

    public DriverServiceImpl(DriverDAO driverDAO, DriverShiftDAO driverShiftDAO, TruckDAO truckDAO, EntityManager entityManager) {
        this.driverDAO = driverDAO;
        this.driverShiftDAO = driverShiftDAO;
        this.entityManager = entityManager;
        this.truckDAO = truckDAO;
    }

    @Override
    public Driver addNewDriver(Driver newDriver) throws LogiwebServiceException, LogiwebValidationException {
        LogiwebValidator.validateDriverFormValues(newDriver);

        try {
            newDriver.setDriverStatus(DriverStatus.REST);

            entityManager.getTransaction().begin();
            Driver driverWithSamePersonalNumber = driverDAO.findDriverByPersonalNumber(newDriver.getPersonalNumber());

            if (driverWithSamePersonalNumber != null) {
                throw new LogiwebValidationException("Driver with this personal number #" + newDriver.getPersonalNumber()
                        + " is already exist.");
            }

            driverDAO.create(newDriver);
            entityManager.getTransaction().commit();

            LOGGER.info("Driver created: " + newDriver.getFirstName() + " " + newDriver.getLastName()
                    + " personal number #" + newDriver.getPersonalNumber() + " , ID: " + newDriver.getDriverId());

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

    @Override
    public Set<Driver> findUnassignedDriversByWorkingHoursAndCity(City city, Double maxWorkingHours) throws LogiwebServiceException {
        try {
            entityManager.getTransaction().begin();

            List<Driver> freeDriversInCity = driverDAO.findByCityWhereNotAssignedToTruck(city);
            List<DriverShift> shiftRecords = driverShiftDAO.findThisMonthRecordsForDrivers(freeDriversInCity);

            entityManager.getTransaction().commit();

            Map<Driver, Double> workingHours = sumWorkingHoursForThisMonth(shiftRecords);

            for (Driver driver : freeDriversInCity) {   //add drivers that don't yet have journals
                if(workingHours.get(driver) == null) {
                    workingHours.put(driver, 0d);
                }
            }

            filterDriversByMaxWorkingHours(workingHours, maxWorkingHours);

            return workingHours.keySet();

        } catch (LogiwebDAOException e) {
            LOGGER.warn(e);
            throw new LogiwebServiceException(e);
        } catch (Exception e) {
            LOGGER.warn(e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    private Map<Driver, Double> sumWorkingHoursForThisMonth(Collection<DriverShift> shiftRecords) {
        Map<Driver, Double> workingHoursForDrivers = new HashMap<Driver, Double>();

        Date firstDayOfCurrentMonth = DateUtil.getFirstDateOfCurrentMonth();
        Date firstDayOfNextMonth = DateUtil.getFirstDayOfNextMonth();

        for (DriverShift ds : shiftRecords) {
            Driver driver = ds.getDriverForThisShiftFK();
            Date shiftBeggined = ds.getDriverShiftBegin();
            Date shiftEnded = ds.getDriverShiftEnd();

            /*
             * If shift ended after or before current month
             * we trim it by limiting shift end to first day of next
             * month (00:00:00) and  shift start to first day of month (00:00:00).
             * if shift is not yet ended -- set Now as upper limit.            *
             */
            if (shiftEnded == null) {
                shiftEnded = new Date();
            }
            if (shiftBeggined.getTime() < firstDayOfCurrentMonth.getTime()) {
                shiftBeggined = firstDayOfCurrentMonth;
            }
            if (shiftEnded.getTime() > firstDayOfNextMonth.getTime()) {
                shiftEnded = firstDayOfNextMonth;
            }

            Double shiftDuration = DateUtil.diffInHours(shiftBeggined, shiftEnded);
            Double totalHoursForDriver = workingHoursForDrivers.get(driver);

            if(totalHoursForDriver == null) {
                workingHoursForDrivers.put(driver, shiftDuration);
            } else {
                workingHoursForDrivers.put(driver, totalHoursForDriver + shiftDuration);
            }
        }

        return workingHoursForDrivers;
    }

    private void filterDriversByMaxWorkingHours(Map<Driver, Double> workingHoursToFilter, Double maxWorkingHours) {
        for (Map.Entry<Driver, Double> entry : workingHoursToFilter.entrySet()) {
            if (entry.getValue() > maxWorkingHours) {
                workingHoursToFilter.remove(entry.getKey());
            }
        }
    }
}
