package com.myprojects.splitbook.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column(name = "ownerind")
    private boolean owner;
    @Column(name = "userid")
    private int userId;         //UserLogin ID
    @ManyToOne
    private Trip trip;
    @Column
    private UserRole role;
    @OneToMany(mappedBy = "contributor")
    private List<Contribution> contributions = new ArrayList<>();
    @ManyToMany(mappedBy = "beneficiaryList")
    private List<Contribution> benefits = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
