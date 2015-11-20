package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface DriverDAO extends AbstractDAO<Driver> {

    Driver findDriverByPersonalNumber(Integer driverPersonalNumber);
}
