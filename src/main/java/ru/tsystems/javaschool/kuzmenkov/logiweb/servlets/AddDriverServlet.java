package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.CityService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebAppResources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by Nikolay on 13.11.2015.
 */
public class AddDriverServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddDriverServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        CityService cityService = LogiwebAppResources.getInstance().getCityService();

        try {
            List<City> cities = cityService.findAllCities();
            req.setAttribute("cities", cities);

        } catch (LogiwebServiceException e) {
            System.out.println("Ex in servlet");
            throw new RuntimeException("Ex in servlet", e);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("private/manager/AddDriver.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DriverService driverService = LogiwebAppResources.getInstance().getDriverService();
        CityService cityService = LogiwebAppResources.getInstance().getCityService();

        try {
            List<City> cities = cityService.findAllCities();
            req.setAttribute("cities", cities);

            Driver newDriver = new Driver();

            Integer personalNumber;
            try {
                personalNumber = Integer.parseInt(req.getParameter("personalNumber"));

            } catch (NumberFormatException | NullPointerException e) {
                throw new LogiwebValidationException("Personal number field is in wrong format. Use integers.");
            }

            Integer cityId;
            try {
                cityId = Integer.parseInt(req.getParameter("city"));

            } catch (NumberFormatException | NullPointerException e) {
                throw new LogiwebValidationException("City selector must return city ID as integer.");
            }

            newDriver.setPersonalNumber(personalNumber);
            newDriver.setFirstName(req.getParameter("firstName"));
            newDriver.setLastName(req.getParameter("lastName"));
            City city = cityService.findCityById(cityId);
            newDriver.setCurrentCityFK(city);

            driverService.addNewDriver(newDriver);

            resp.sendRedirect(req.getContextPath() + "/DriverList");

        } catch (LogiwebValidationException e) {
            req.setAttribute("error", e.getMessage());
            RequestDispatcher rq = req.getRequestDispatcher("private/manager/AddDriver.jsp");
            rq.forward(req, resp);
        } catch (LogiwebServiceException e) {
            e.printStackTrace();
        }
    }
}
