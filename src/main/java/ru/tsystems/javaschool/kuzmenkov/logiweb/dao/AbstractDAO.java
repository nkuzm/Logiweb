package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;

import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public interface AbstractDAO {

    void addDriver(Driver newDriver, Integer personalNumber) throws SQLException;

    void closeConnection();
}
