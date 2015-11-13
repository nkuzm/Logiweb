package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class EntityManagerTestApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("logiwebPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Driver driver = new Driver("Иван", "Иванов", 123, 0, DriverStatus.REST);

            tx.begin();
            em.persist(driver);
            tx.commit();
        } finally {
            System.out.println("Закрываем connection.");
            em.close();
            emf.close();
            System.out.println("Connection успешно закрыто.");
        }
    }
}
