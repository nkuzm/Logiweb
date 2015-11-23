package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class DriverServiceImpl implements DriverService {

    private EntityManager entityManager;
    private DriverDAO driverDAO;
    private TruckDAO truckDAO;

    public DriverServiceImpl(DriverDAO driverDAO, TruckDAO truckDAO, EntityManager entityManager) {
        this.driverDAO = driverDAO;
        this.entityManager = entityManager;
        this.truckDAO = truckDAO;
    }

    @Override
    public void addDriver(Driver newDriver) throws SQLException {
        newDriver.setDriverStatus(DriverStatus.REST);

        try {
            entityManager.getTransaction().begin();
            Driver driverWithSamePersonalNumber = driverDAO.findDriverByPersonalNumber(newDriver.getPersonalNumber());

            if (driverWithSamePersonalNumber != null) {
                System.out.println("Driver with this personal number ID " + newDriver.getPersonalNumber() + " is already in use.");
            }

            driverDAO.create(newDriver);
            entityManager.getTransaction().commit();

            System.out.println("Driver created. " + newDriver.getFirstName() + " "
                    + newDriver.getLastName()
                    + " ID#" + newDriver.getPersonalNumber());

        } catch (Exception e) {
            System.out.println("Exception in DriverServiceImpl.");
            e.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<Driver> findAllDrivers() {
        List<Driver> driverList = null;

        try {
            entityManager.getTransaction().begin();
            driverList = driverDAO.findAll();
            entityManager.getTransaction().commit();

            return driverList;

        } catch (Exception e) {
            System.out.println("Exception in DriverServiceImpl.");
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return driverList;
    }
}
