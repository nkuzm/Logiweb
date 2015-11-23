package ru.tsystems.javaschool.kuzmenkov.logiweb.util;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.City;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Driver;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Order;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.OrderStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.TruckStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.DriverService;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.OrderService;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Created by Nikolay on 13.11.2015.
 */
public class EntityManagerTestApp {

    public static void main(String[] args) throws SQLException, LogiwebValidationException, LogiwebServiceException {

        try {
            EntityManager em = LogiwebAppResources.getInstance().getEntityManager();

            OrderService orderService = LogiwebAppResources.getInstance().getOrderService();

            Truck truck = new Truck();
            Order order = new Order();
            City city = em.find(City.class, 1);

            order.setOrderStatus(OrderStatus.CREATED);

            truck.setTruckId(14);
            truck.setTruckNumber("aaa111");
            truck.setDriverCount(2);
            truck.setCapacity((double) 2000);
            truck.setTruckStatus(TruckStatus.WORKING);
            truck.setCurrentCityFK(city);

            /*em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();*/

            orderService.assignTruckToOrder(truck, 15);

        } finally {
            System.out.println("Закрываем connection.");
            LogiwebAppResources.getInstance().getEntityManager().close();
            LogiwebAppResources.getInstance().getEntityManagerFactory().close();
            System.out.println("Connection успешно закрыто.");
        }
    }
}
