package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.CityService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebAppResources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikolay on 26.11.2015.
 */
public class AddTruckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CityService cityService = LogiwebAppResources.getInstance().getCityService();

        try {
            List<City> cities = cityService.findAllCities();
            req.setAttribute("cities", cities);
        } catch (LogiwebServiceException e) {
            System.out.println("Ex in servlet");
            throw new RuntimeException("Ex in servlet", e);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("private/manager/AddTruck.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
