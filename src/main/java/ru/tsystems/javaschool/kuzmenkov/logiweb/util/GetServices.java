package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.DriverDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl.DriverDAOImpl;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl.DriverServiceImpl;

/**
 * Created by Nikolay on 18.11.2015.
 */
public class GetServices {

    private LogiwebAppResources lwAppR = LogiwebAppResources.getInstance();

    private DriverDAO driverDAO;

    private DriverService driverService;

    private DriverDAO getDriverDAO() {
        driverDAO = new DriverDAOImpl(Driver.class, lwAppR.getEntityManager());

        return driverDAO;
    }

    public DriverService getDriverService() {
        driverService = new DriverServiceImpl(getDriverDAO(), lwAppR.getEntityManager());

        return driverService;
    }
}
