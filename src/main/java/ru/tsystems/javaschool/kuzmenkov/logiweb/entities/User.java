package ru.tsystems.javaschool.kuzmenkov.logiweb.entities;

import ru.tsystems.javaschool.kuzmenkov.logiweb.entities.status.Role;

import javax.persistence.*;

/**
 * Created by Nikolay on 14.11.2015.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", unique = true)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;

    public User() {

    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}
