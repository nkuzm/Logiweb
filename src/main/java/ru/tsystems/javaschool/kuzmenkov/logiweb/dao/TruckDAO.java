package ru.tsystems.javaschool.kuzmenkov.logiweb.dao;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;

/**
 * Created by Nikolay on 20.11.2015.
 */
public interface TruckDAO extends AbstractDAO<Truck> {

    Truck findTruckByTruckNumber(String truckNumber);
}
