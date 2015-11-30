package ru.tsystems.javaschool.kuzmenkov.logiweb.servlets;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Order;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.OrderRoute;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.OrderStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.CityService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.OrderService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.TruckService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.LogiwebAppResources;
import ru.tsystems.javaschool.kuzmenkov.logiweb.util.RouteInfoForOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Nikolay Kuzmenkov.
 */
public class EditOrderServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderService orderService = LogiwebAppResources.getInstance().getOrderService();
        CityService cityService = LogiwebAppResources.getInstance().getCityService();
        TruckService truckService = LogiwebAppResources.getInstance().getTruckService();

        OrderRoute orderRoute = null;
        Order order = null;
        Integer orderId;

        if("new".equals(req.getParameter("action"))) {
            Order newOrder = new Order();
            newOrder.setOrderStatus(OrderStatus.CREATED);

            try {
                orderService.addNewOrder(newOrder);

            } catch (LogiwebServiceException e) {
                LOGGER.warn("Unexpected exception in EditOrderSrvlet", e);
            }

            orderId = newOrder.getOrderId();
        }
        else {
            orderId = Integer.parseInt(req.getParameter("orderId"));
        }

        try {
            order = orderService.findOrderById(orderId);
            if (order == null) throw new IllegalArgumentException("Order #" + orderId + " not exist.");

            orderRoute = RouteInfoForOrder.getRouteInformationForOrder(order);

            req.setAttribute("orderId", orderId);
            req.setAttribute("order", order);
            req.setAttribute("orderRoute", orderRoute);
            req.setAttribute("maxWorkingHoursLimit", RouteInfoForOrder.MAX_WORKING_HOURS_LIMIT);

            req.setAttribute("cities", cityService.findAllCities());

            } catch (LogiwebServiceException e) {
                e.printStackTrace();
            }

        //suggest trucks
        try {
            if(order.getAssignedTruckFK() == null) {
                List<Truck> suggestedTrucks = truckService.findFreeAndUnbrokenByCargoCapacity(orderRoute.getMaxWeightOnCourse());
                req.setAttribute("suggestedTrucks", suggestedTrucks);
            }

        } catch (LogiwebServiceException e) {
            e.printStackTrace();
        }

        req.setAttribute("statuses", OrderStatus.values());

        RequestDispatcher rd = req.getRequestDispatcher("/private/manager/EditOrder.jsp");
        rd.forward(req, resp);
    }
}
