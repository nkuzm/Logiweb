package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebAppResources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikolay Kuzmenkov.
 */
public class DriverListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DriverService driverService = LogiwebAppResources.getInstance().getDriverService();

        try {
            List<Driver> drivers = driverService.findAllDrivers();
            req.setAttribute("drivers", drivers);

            Map<Driver, Double> workingHoursForDrivers = new HashMap<Driver, Double>();
            //for (Driver driver : drivers) {
             //   workingHoursForDrivers.put(driver, driverService.calculateWorkingHoursForDriver(driver));
            //}
            //mav.addObject("workingHoursForDrivers", workingHoursForDrivers);

            RequestDispatcher rd = req.getRequestDispatcher("private/manager/DriverList.jsp");
            rd.forward(req, resp);

        } catch (LogiwebServiceException e) {
            //LOG.warn(e);
            throw new RuntimeException("Unrecoverable server exception.", e);
        }

    }
}
