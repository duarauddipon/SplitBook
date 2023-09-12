package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
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

    public static final String query_find_members_trip = "SELECT m FROM Member m where m.trip.id = : tripid";

    public boolean insertTrip(Trip trip, Member owner)
    {
        try {
            trip.addNewMember(owner);
            owner.setTrip(trip);
            entityManager.persist(owner);
            entityManager.merge(trip);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        return false;
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

    public Trip getTripById(int id)
    {
        TypedQuery<Trip> query = entityManager.createNamedQuery("query_find_by_id", Trip.class);
        query.setParameter("id",id);
        List<Trip> resultList = query.getResultList();

        if(resultList.isEmpty())
        {
            return null;
        }
        return resultList.get(0);
    }

    public void insertMember(Trip trip,Member member)
    {
        trip.addNewMember(member);
        member.setTrip(trip);
        try{
            entityManager.persist(member);
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }

    public List<Member> findMembersByTripId(int tripId)
    {
        TypedQuery<Member> query = entityManager.createQuery(query_find_members_trip, Member.class);
        query.setParameter("tripid",tripId);
        List<Member> resultList = query.getResultList();

        return resultList;
    }

    public Member findMemberByMemberId(int memberId)
    {
        try {
            TypedQuery<Member> query = entityManager.createNamedQuery("query_find_member_by_id",Member.class);
            query.setParameter("mid",memberId);
            Member member = query.getSingleResult();
            return member;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }

        return null;
    }

    public void insertContribution(Contribution contribution, List<Member> beneficiaries, Member contributor)
    {
        try
        {
            //contribution.setContributor(contributor);    //already been set by modelAttribute
            contributor.addContribution(contribution);
            //entityManager.merge(contributor);
            for(Member b : beneficiaries)
            {
                contribution.addBeneficiary(b);
                b.addBenefit(contribution);
                //entityManager.merge(b);
            }
            entityManager.merge(contribution);

        }
        catch (Exception ex)
        {
            System.out.println("See here -> "+ex.getMessage());
        }
    }
}
