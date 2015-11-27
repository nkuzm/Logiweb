package ru.tsystems.javaschool.kuzmenkov.logiweb.services;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;

import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface DriverService {

    Driver addNewDriver(Driver newDriver) throws LogiwebServiceException, LogiwebValidationException;

    /**
     * @param editedDriver
     * @throws LogiwebServiceException
     */
    void editDriver(Driver editedDriver) throws LogiwebServiceException;

    /**
     * @return
     * @throws LogiwebServiceException
     */
    List<Driver> findAllDrivers() throws LogiwebServiceException;

    Driver findDriverById(Integer driverId) throws LogiwebServiceException;

    void deleteDriver(Driver deletedDriver) throws LogiwebServiceException;

    //List<Driver> findUnassignedDriversByWorkingHoursAndCity(City city, Double maxWorkingHours) throws LogiwebServiceException;
}
