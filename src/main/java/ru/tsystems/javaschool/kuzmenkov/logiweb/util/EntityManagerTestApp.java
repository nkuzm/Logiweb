package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Order;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.OrderStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.TruckStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.OrderService;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class EntityManagerTestApp {

    public static void main(String[] args) throws SQLException, LogiwebValidationException, LogiwebServiceException {

        try {
            EntityManager em = LogiwebAppResources.getInstance().getEntityManager();

            DriverService driverService = LogiwebAppResources.getInstance().getDriverService();

            Driver driver = new Driver();

            driver.setPersonalNumber(1212);
            driver.setFirstName("Paul");
            driver.setLastName("Pogba");

            City city = em.find(City.class, 2);
            driver.setCurrentCityFK(city);

            /*em.getTransaction().begin();
            em.persist();
            em.getTransaction().commit();*/

            driverService.addNewDriver(driver);
            System.out.println("Success");

        } finally {
            System.out.println("Закрываем connection.");
            LogiwebAppResources.getInstance().getEntityManager().close();
            LogiwebAppResources.getInstance().getEntityManagerFactory().close();
            System.out.println("Connection успешно закрыто.");
        }
    }
}
