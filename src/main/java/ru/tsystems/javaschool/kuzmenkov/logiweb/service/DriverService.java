package ru.tsystems.javaschool.kuzmenkov.logiweb.service;

import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface DriverService {

    void addDriver(String firstName, String lastName, Integer personalNumber) throws SQLException;
}
