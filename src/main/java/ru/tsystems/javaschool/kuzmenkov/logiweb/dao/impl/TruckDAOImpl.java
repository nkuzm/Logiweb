package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Nikolay on 20.11.2015.
 */
public class TruckDAOImpl extends AbstractDAOImpl<Truck> implements TruckDAO {

    public TruckDAOImpl(Class<Truck> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Override
    public Truck findTruckByTruckNumber(String truckNumber) {
        Truck queryResult = null;

        try {
            EntityManager em = getEntityManager();

            Query query = em.createQuery("SELECT t FROM Truck t WHERE t.truckNumber = :truckNumber", Truck.class);
            query.setParameter("truckNumber", truckNumber);
            List<Truck> resultList = query.getResultList();

            if(!resultList.isEmpty()) {
                queryResult = resultList.get(0);
            }

        } catch (Exception e) {
            System.out.println("Exception in TruckDAOImpl.");;
        }

        return queryResult;
    }
}
