package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets.filters;

import ru.tsystems.javaschool.kuzmenkov.logiweb.util.AuthorizationUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nikolay on 24.11.2015.
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        boolean loggedIn = AuthorizationUtil.userIsLoggedIn((HttpServletRequest) servletRequest);

        if (loggedIn) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            try {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/private/Login.jsp");
            } catch (IOException e) {
                //LOG.warn("IO exception", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
