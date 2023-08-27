package com.myprojects.splitbook.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column
    private BigDecimal amount;

    @ManyToOne
    private Member contributor;

    @ManyToMany
    @JoinTable(name = "contributionbeneficiary",
                joinColumns = @JoinColumn(name = "contributionid"),
                inverseJoinColumns = @JoinColumn(name = "memberid"))
    private List<Member> beneficiaryList = new ArrayList<>();

    @ManyToOne
    private Trip trip;

}
