package ru.tsystems.javaschool.kuzmenkov.logiweb.entities;

import javax.persistence.*;

/**
 * Created by Nikolay on 13.11.2015. Class ��� �������� "����" (1� �������, ��� ������).
 */
@Entity
@Table(name = "truck")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer truckId;
}
