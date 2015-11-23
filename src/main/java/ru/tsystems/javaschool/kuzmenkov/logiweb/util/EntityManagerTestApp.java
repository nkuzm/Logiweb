package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;

import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class EntityManagerTestApp {

    public static void main(String[] args) throws SQLException {

        try {


            Driver driver = new Driver();
            driver.setFirstName("Vasya");
            driver.setLastName("Ivanov");
            driver.setPersonalNumber(1235);
            driver.setDriverStatus(DriverStatus.REST);
            driver.setCurrentCityFK(null);

        } finally {
            System.out.println("Закрываем connection.");
            LogiwebAppResources.getInstance().getEntityManager().close();
            LogiwebAppResources.getInstance().getEntityManagerFactory().close();
            System.out.println("Connection успешно закрыто.");
        }

        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("logiwebPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            City newCity = new City();
            newCity.setName("Pskov");

            tx.begin();
            em.persist(newCity);
            tx.commit();

        } finally {
            System.out.println("Закрываем connection.");
            em.close();
            emf.close();
            System.out.println("Connection успешно закрыто.");
        }*/
    }
}
