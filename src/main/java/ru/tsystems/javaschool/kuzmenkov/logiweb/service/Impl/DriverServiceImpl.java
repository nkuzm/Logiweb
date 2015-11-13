package ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.AbstractDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl.AbstractDAOImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.DriverService;

import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverServiceImpl implements DriverService {

    private AbstractDAO abstractDAO = new AbstractDAOImpl();

    @Override
    public void addDriver(String firstName, String lastName, Integer personalNumber) throws SQLException {
        Driver newDriver = createNewDriver(firstName, lastName, personalNumber);

        abstractDAO.addDriver(newDriver, personalNumber);
        abstractDAO.closeConnection();
    }

    private Driver createNewDriver(String firstName, String lastName, Integer personalNumber) {
        Driver newDriver = new Driver();
        newDriver.setFirstName(firstName);
        newDriver.setLastName(lastName);
        newDriver.setPersonalNumber(personalNumber);

        return newDriver;
    }
}
