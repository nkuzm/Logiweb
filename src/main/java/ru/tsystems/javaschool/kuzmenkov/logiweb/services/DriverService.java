package ru.tsystems.javaschool.kuzmenkov.logiweb.services;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;

import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface DriverService {

    void editDriver(Driver editedDriver) throws LogiwebServiceException;

    List<Driver> findAllDrivers() throws LogiwebServiceException;

    Driver findDriverById(Integer driverId) throws LogiwebServiceException;

    Driver addNewDriver(Driver newDriver) throws LogiwebServiceException;

    void deleteDriver(Driver deletedDriver) throws LogiwebServiceException;
}
