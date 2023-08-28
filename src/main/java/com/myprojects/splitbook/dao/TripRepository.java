package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TripRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Trip insertTrip(Trip trip)
    {
        return entityManager.merge(trip);
    }

    public List<Trip> getTripsByOwnerId(int id)
    {
        TypedQuery<Trip> query = entityManager.createNamedQuery("query_find_by_owner", Trip.class);
        query.setParameter("ownerid",id);
        List<Trip> resultList = query.getResultList();

        if(resultList.isEmpty())
        {
            return null;
        }
        return resultList;
    }
}
