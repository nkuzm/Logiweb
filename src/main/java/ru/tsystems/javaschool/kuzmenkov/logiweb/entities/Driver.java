package ru.tsystems.javaschool.kuzmenkov.logiweb.entities;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.DriverStatus;

import javax.persistence.*;

/**
 * Created by Nikolay on 13.11.2015. Class для сущности "Водитель" из итогового задания (1й вариант, без связей).
 */
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driver_id")
    private Integer driverId;

    private String firstName;

    private String lastName;

    private Integer personalNumber;

    private Integer hoursWorked;

    private DriverStatus driverStatus;

    public Driver() {
    }

    public Driver(String firstName, String lastName, Integer personalNumber, Integer hoursWorked, DriverStatus driverStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Integer personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }
}
