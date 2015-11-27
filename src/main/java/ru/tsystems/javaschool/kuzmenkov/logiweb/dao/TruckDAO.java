package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

/**
 * Operations for Truck entity.
 *
 * @author Nikolay Kuzmenkov.
 */
public interface TruckDAO extends AbstractDAO<Truck> {


    Truck findTruckByTruckNumber(String truckNumber) throws LogiwebDAOException;
}
