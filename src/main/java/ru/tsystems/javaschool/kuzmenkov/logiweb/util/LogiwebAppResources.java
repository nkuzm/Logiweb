package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Nikolay on 18.11.2015.
 */
public final class LogiwebAppResources {

    private static class SingletonHolder {
        private static final LogiwebAppResources SINGLETON_INSTANCE = new LogiwebAppResources();
    }

    private static final String PERSISTENCE_UNIT = "logiwebPU";

    volatile private EntityManagerFactory entityManagerFactory;
    volatile private EntityManager entityManager;

    private LogiwebAppResources() {

    }

    public static LogiwebAppResources getInstance() {
        return SingletonHolder.SINGLETON_INSTANCE;
    }

    private EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null) {
            synchronized(this) {
                if(entityManagerFactory == null) {
                    entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                }
            }
        }

        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        if(entityManager == null || !entityManager.isOpen()) {
            synchronized(this) {
                if(entityManager == null || !entityManager.isOpen()) {
                    entityManager = getEntityManagerFactory().createEntityManager();
                }
            }
        }

        return entityManager;
    }
}
