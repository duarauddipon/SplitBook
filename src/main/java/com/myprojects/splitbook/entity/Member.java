package com.myprojects.splitbook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "query_find_member_by_id", query = "select m from Member m where m.id = :mid")})
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
    @JsonManagedReference
    @ManyToOne
    private Trip trip;
    @Column
    private UserRole role;
    @OneToMany(mappedBy = "contributor",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Contribution> contributions = new ArrayList<>();
    @ManyToMany(mappedBy = "beneficiaryList",cascade=CascadeType.ALL)
    @JsonManagedReference
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

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
    }

    public List<Contribution> getBenefits() {
        return benefits;
    }

    public void addBenefit(Contribution benefit) {
        benefits.add(benefit);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", userId=" + userId +
                ", trip=" + trip +
                ", role=" + role +
                ", contributions=" + contributions +
                ", benefits=" + benefits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id && owner == member.owner && userId == member.userId && Objects.equals(name, member.name) && Objects.equals(trip, member.trip) && role == member.role && Objects.equals(contributions, member.contributions) && Objects.equals(benefits, member.benefits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, userId, trip, role, contributions, benefits);
    }
}
