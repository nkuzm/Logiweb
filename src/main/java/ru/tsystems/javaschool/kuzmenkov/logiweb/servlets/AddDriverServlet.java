package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import ru.tsystems.javaschool.kuzmenkov.logiweb.service.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.service.Impl.DriverServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class AddDriverServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AddDriverServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        try {
            resp.setContentType("text/html");

            Integer personalNumber = Integer.parseInt(req.getParameter("personalnumber"));
            String firstName = req.getParameter("firstname");
            String lastName = req.getParameter("lastname");

            DriverService driverService = new DriverServiceImpl();

            if (!validateParameters(firstName.toLowerCase()) || !validateParameters(lastName.toLowerCase())) {
                throw new IllegalArgumentException("Incorrect format");
            }

            driverService.addDriver(firstName.toLowerCase(), lastName.toLowerCase(), personalNumber);
            out.println("Driver " + firstName + " " + lastName + " " + "was add successfully.");

        } catch (Exception e) {
            out.println(e.getMessage());
            logger.log(Level.SEVERE, "Exception: ", e);
        }
    }

    private boolean validateParameters(String testString) {
        Pattern p = Pattern.compile("^[a-z]*$");
        Matcher m = p.matcher(testString);
        return m.matches();
    }
}
