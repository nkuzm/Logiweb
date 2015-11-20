package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl.DriverDAOImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl.TruckDAOImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl.DriverServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl.TruckServiceImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.TruckService;

/**
 * Created by Nikolay on 18.11.2015.
 */
public class GetServices {

    private LogiwebAppResources lwAppR = LogiwebAppResources.getInstance();

    private DriverDAO driverDAO;
    private TruckDAO truckDAO;

    private DriverService driverService;
    private TruckService truckService;

    private DriverDAO getDriverDAO() {
        driverDAO = new DriverDAOImpl(Driver.class, lwAppR.getEntityManager());

        return driverDAO;
    }

    public DriverService getDriverService() {
        driverService = new DriverServiceImpl(getDriverDAO(), getTruckDao(), lwAppR.getEntityManager());

        return driverService;
    }

    private TruckDAO getTruckDao() {
        truckDAO = new TruckDAOImpl(Truck.class, lwAppR.getEntityManager());

        return truckDAO;
    }

    public TruckService getTruckService() {
        truckService = new TruckServiceImpl(getTruckDao(), lwAppR.getEntityManager());

        return truckService;
    }
}
