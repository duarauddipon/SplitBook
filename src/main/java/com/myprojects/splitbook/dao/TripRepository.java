package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TripRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Trip insertTrip(Trip trip)
    {
        return entityManager.merge(trip);
    }
}
