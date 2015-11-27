package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Nikolay on 24.11.2015.
 */
public class AuthorizationUtil {

    public static final String SESSION_USER_ROLE_ATTR = "userRole";

    private AuthorizationUtil() {
    }

    public static boolean userIsLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(SESSION_USER_ROLE_ATTR) != null;
    }

    public static void startAuthSessionForUserRole(HttpSession session, Role userRole) {
        session.setAttribute(SESSION_USER_ROLE_ATTR, userRole);
    }
}
