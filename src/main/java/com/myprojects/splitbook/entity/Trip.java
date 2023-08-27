package com.myprojects.splitbook.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "trip")
    private List<Member> members = new ArrayList<>();

    @Column(name = "ownerid")       //UserLogin ID
    private int ownerId;

    @Column(name = "totalexpense")
    private BigDecimal totalExpense;

    @OneToMany(mappedBy = "trip")
    private List<Contribution> contributions = new ArrayList<>();

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

    public List<Member> getMembers() {
        return members;
    }

    public void addNewMember(Member member) {
        members.add(member);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
    }
}