package ru.tsystems.javaschool.kuzmenkov.logiweb.services.Impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.FreightDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.TruckStatus;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.OrderDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Freight;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Order;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebServiceException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebValidationException;
import ru.tsystems.javaschool.kuzmenkov.logiweb.services.OrderService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Nikolay on 23.11.2015.
 */
public class OrderServiceImpl implements OrderService {

    private EntityManager entityManager;

    private FreightDAO freightDAO;
    private OrderDAO orderDAO;
    private TruckDAO truckDAO;

    public OrderServiceImpl(FreightDAO freightDAO, OrderDAO orderDAO, TruckDAO truckDAO, EntityManager entityManager) {
        this.freightDAO = freightDAO;
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAllOrders() throws LogiwebServiceException {
        List<Order> allOrdersResult;

        try {
            entityManager.getTransaction().begin();
            allOrdersResult = orderDAO.findAll();
            entityManager.getTransaction().commit();

            return allOrdersResult;

        } catch(LogiwebDAOException e) {
            System.out.println("Exception in OrderServiceImpl");
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<Freight> findAllFreights() throws LogiwebServiceException {
        List<Freight> allFreightsResult;

        try {
            entityManager.getTransaction().begin();
            allFreightsResult = freightDAO.findAll();
            entityManager.getTransaction().rollback();

            return allFreightsResult;

        } catch(LogiwebDAOException e) {
            System.out.println("Exception in OrderServiceImpl");
            throw new LogiwebServiceException(e);
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public Order addNewOrder(Order newOrder) throws LogiwebServiceException {
        try {
            entityManager.getTransaction().begin();
            orderDAO.create(newOrder);
            entityManager.getTransaction().commit();

            return newOrder;

        } catch(LogiwebDAOException e) {
            System.out.println("Exception in OrderServiceImpl");
            throw new LogiwebServiceException(e);
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    @Override
    public Order findOrderById(Integer orderId) throws LogiwebServiceException {
        return null;
    }

    @Override
    public void addNewFreight(Freight newFreight) throws LogiwebServiceException {

    }

    @Override
    public void assignTruckToOrder(Truck assignedTruck, Integer orderId) throws LogiwebServiceException,
            LogiwebValidationException {

        try {
            if(assignedTruck == null) {
                throw new LogiwebValidationException("Selected truck does not exist.");
            }

            entityManager.getTransaction().begin();
            assignedTruck = truckDAO.findById(assignedTruck.getTruckId());

            if(assignedTruck == null) {
                throw new LogiwebValidationException("Selected truck does not exist.");
            }
            else if(assignedTruck.getTruckStatus() != TruckStatus.WORKING) {
                throw new LogiwebValidationException("Selected truck must have TruckStatus 'WORKING'.");
            }
            else if(assignedTruck.getOrderForThisTruck() != null) {
                throw new LogiwebValidationException("Selected truck must have no assigned orders.");
            }

            Order orderForTruck = orderDAO.findById(orderId);

            if(orderForTruck == null) {
                throw new LogiwebValidationException("Order with ID " + orderId + " does not exist.");
            }
            else if (orderForTruck.getAssignedTruckFK() != null) {
                throw new LogiwebValidationException("Order with ID " + orderId + " must not be assigned to another truck.");
            }
            else if (orderForTruck.getOrderLines() == null || orderForTruck.getOrderLines().isEmpty()) {
                throw new LogiwebValidationException("Order with ID " + orderId + " must have freight.");
            }

            assignedTruck.setOrderForThisTruck(orderForTruck);
            orderForTruck.setAssignedTruckFK(assignedTruck);
            orderDAO.update(orderForTruck);
            truckDAO.update(assignedTruck);

            System.out.println("Truck with ID" + assignedTruck.getTruckId() + " assign to order " + orderForTruck.getOrderId());

            entityManager.getTransaction().commit();

        } catch(LogiwebDAOException e) {
            System.out.println("Exception in OrderServiceImpl");
            throw new LogiwebServiceException(e);
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
