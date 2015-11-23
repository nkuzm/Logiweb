package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.TruckService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.TruckStatus;

import javax.persistence.EntityManager;

/**
 * Created by Nikolay on 20.11.2015.
 */
public class TruckServiceImpl implements TruckService {

    private EntityManager entityManager;
    private TruckDAO truckDAO;

    public TruckServiceImpl(TruckDAO truckDAO, EntityManager entityManager) {
        this.truckDAO = truckDAO;
        this.entityManager = entityManager;
    }

    @Override
    public void addTruck(Truck newTruck) {
        newTruck.setTruckStatus(TruckStatus.WORKING);

        try {
            entityManager.getTransaction().begin();
            Truck truckWithSameTruckNumber = truckDAO.findTruckByTruckNumber(newTruck.getTruckNumber());

            if (truckWithSameTruckNumber != null) {
                throw new Exception("Truck with this number "
                        + newTruck.getTruckNumber() + " is already in use.");
            }

            truckDAO.create(newTruck);
            entityManager.getTransaction().commit();

            System.out.println("New truck created. Truck number: " + newTruck.getTruckNumber());

        } catch (Exception e) {
            System.out.println("Exception in TruckServiceImpl.");
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
