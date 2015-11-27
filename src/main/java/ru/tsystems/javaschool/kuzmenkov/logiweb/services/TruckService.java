package ru.tsystems.javaschool.kuzmenkov.logiweb.services;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;

/**
 * Business logic related to trucks.
 *
 * @author Nikolay Kuzmenkov.
 */
public interface TruckService {

    /**
     * Add new truck.
     *
     * @param newTruck
     * @return same truck.
     * @throws LogiwebServiceException if unexpected exception occurred on lower level (not user fault).
     * @throws LogiwebValidationException if truck don't have all required fields or not unique truck number.
     */
    Truck addNewTruck(Truck newTruck) throws LogiwebServiceException, LogiwebValidationException;
}
