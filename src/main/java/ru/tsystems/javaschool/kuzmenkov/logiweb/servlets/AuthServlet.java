package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.User;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.UserService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.AuthorizationUtil;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebAppResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nikolay on 22.11.2015.
 */
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        if(AuthorizationUtil.userIsLoggedIn(req)) {
            redirectToActionPage(req, resp);
        }
        else resp.sendRedirect("Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = LogiwebAppResources.getInstance().getUserService();

        try {
            if (email != null) {
                User user = userService.getUserByEmailAndPassword(email, password);
                AuthorizationUtil.startAuthSessionForUserRole(req.getSession(), user.getUserRole());
                System.out.println("User id:" + user.getUserId() + " mail:" + user.getUserEmail() + " is logged in.");
                //redirectToFrontPage(request, response);
            }
        } catch (LogiwebServiceException e) {
            req.setAttribute("error", "User is not found");
        }
    }

    private void redirectToActionPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("addDriverr.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

