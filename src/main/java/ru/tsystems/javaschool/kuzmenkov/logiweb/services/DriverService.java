package ru.tsystems.javaschool.kuzmenkov.logiweb.services;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface DriverService {

    void addDriver(Driver newDriver) throws SQLException;

    List<Driver> findAllDrivers();
}
