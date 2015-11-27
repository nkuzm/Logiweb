package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.TruckService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.TruckStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebValidator;

import javax.persistence.EntityManager;

/**
 * Business logic related to trucks (implementation).
 *
 * @author Nikolay Kuzmenkov.
 */
public class TruckServiceImpl implements TruckService {

    private static final Logger LOGGGER = Logger.getLogger(TruckServiceImpl.class);

    private EntityManager entityManager;
    private TruckDAO truckDAO;

    public TruckServiceImpl(TruckDAO truckDAO, EntityManager entityManager) {
        this.truckDAO = truckDAO;
        this.entityManager = entityManager;
    }

    /**
     * Add new truck.
     *
     * @param newTruck
     * @return same truck.
     * @throws LogiwebServiceException if unexpected exception occurred on lower level (not user fault).
     * @throws LogiwebValidationException if truck don't have all required fields or not unique truck number.
     */

    @Override
    public Truck addNewTruck(Truck newTruck) throws LogiwebServiceException, LogiwebValidationException {
        LogiwebValidator.validateTruckFormValues(newTruck);

        if (!LogiwebValidator.validateTruckNumber(newTruck.getTruckNumber())) {
            throw new LogiwebValidationException("Truck number #" + newTruck.getTruckNumber() + " is not valid.");
        }

        try {
            newTruck.setTruckStatus(TruckStatus.WORKING);

            entityManager.getTransaction().begin();
            Truck truckWithTruckNumber = truckDAO.findTruckByTruckNumber(newTruck.getTruckNumber());

            if (truckWithTruckNumber != null) {
                throw new LogiwebValidationException("Truck with number #" + newTruck.getTruckNumber() + " is already exist.");
            }

            truckDAO.create(newTruck);
            entityManager.getTransaction().commit();

            LOGGGER.info("Truck created: truck number #" + newTruck.getTruckNumber() + " ID: " + newTruck.getTruckId());


        } catch (LogiwebDAOException e) {
            LOGGGER.warn("Exception in TruckServiceImpl - addNewTruck().", e);
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return newTruck;
    }
}
