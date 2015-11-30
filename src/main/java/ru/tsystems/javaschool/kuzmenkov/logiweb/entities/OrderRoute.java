package ru.tsystems.javaschool.kuzmenkov.logiweb.entities;

import java.util.List;

/**
 * @author Nikolay Kuzmenkov.
 */
public class OrderRoute {

    private double estimatedTime;
    private double maxWeightOnCourse;
    private List<WayPoint> bestOrderOfDelivery;

    public OrderRoute(double estimatedTime, double maxWeightOnCourse, List<WayPoint> bestOrderOfDelivery) {
        this.estimatedTime = estimatedTime;
        this.maxWeightOnCourse = maxWeightOnCourse;
        this.bestOrderOfDelivery = bestOrderOfDelivery;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public double getMaxWeightOnCourse() {
        return maxWeightOnCourse;
    }

    public List<WayPoint> getBestOrderOfDelivery() {
        return bestOrderOfDelivery;
    }
}
