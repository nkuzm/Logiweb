package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.*;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl.*;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.*;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl.DriverServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl.OrderServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl.TruckServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl.UserServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.OrderService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.TruckService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.UserService;

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

    private DriverDAO driverDAO;
    private TruckDAO truckDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private FreightDAO freightDAO;

    private DriverService driverService;
    private TruckService truckService;
    private UserService userService;
    private OrderService orderService;

    private LogiwebAppResources() {

    }

    public static LogiwebAppResources getInstance() {
        return SingletonHolder.SINGLETON_INSTANCE;
    }

    public EntityManagerFactory getEntityManagerFactory() {
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

    private DriverDAO getDriverDAO() {
        if(driverDAO == null) {
            driverDAO = new DriverDAOImpl(Driver.class, getEntityManager());
        }

        return driverDAO;
    }

    public DriverService getDriverService() {
        if(driverService == null) {
            driverService = new DriverServiceImpl(getDriverDAO(), getTruckDAO(), getEntityManager());
        }

        return driverService;
    }

    private TruckDAO getTruckDAO() {
        if(truckDAO == null) {
            truckDAO = new TruckDAOImpl(Truck.class, getEntityManager());
        }

        return truckDAO;
    }

    public TruckService getTruckService() {
        if(truckService == null) {
            truckService = new TruckServiceImpl(getTruckDAO(), getEntityManager());
        }

        return truckService;
    }

    public FreightDAO getFreightDAO() {
        if(freightDAO == null) {
            freightDAO = new FreightDAOImpl(Freight.class, getEntityManager());
        }

        return freightDAO;
    }

    public OrderDAO getOrderDAO() {
        if(orderDAO == null) {
            orderDAO = new OrderDAOImpl(Order.class, getEntityManager());
        }

        return orderDAO;
    }

    public OrderService getOrderService() {
        if(orderService == null) {
            orderService = new OrderServiceImpl(getFreightDAO(), getOrderDAO(), getTruckDAO(), getEntityManager());
        }

        return orderService;
    }

    public UserDAO getUserDAO() {
        if(userDAO == null) {
            userDAO = new UserDAOImpl(User.class, getEntityManager());
        }

        return userDAO;
    }

    public UserService getUserService() {
        if(userService == null) {
            userService = new UserServiceImpl(getEntityManager(),
                            getUserDAO());

        }

        return userService;
    }
}
