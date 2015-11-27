package ru.tsystems.javaschool.kuzmenkov.logiweb.dao.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.kuzmenkov.logiweb.dao.TruckDAO;
import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.Truck;
import ru.tsystems.javaschool.kuzmenkov.logiweb.exceptions.LogiwebDAOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Operations for Truck entity (implementation).
 *
 * @author Nikolay Kuzmenkov.
 */
public class TruckDAOImpl extends AbstractDAOImpl<Truck> implements TruckDAO {

    private static final Logger LOGGGER = Logger.getLogger(TruckDAOImpl.class);

    public TruckDAOImpl(Class<Truck> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Override
    public Truck findTruckByTruckNumber(String truckNumber) throws LogiwebDAOException {
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
            LOGGGER.warn("Exception in TruckDAOImpl - findTruckByTruckNumber().", e);
            throw new LogiwebDAOException(e);
        }

        return queryResult;
    }
}
