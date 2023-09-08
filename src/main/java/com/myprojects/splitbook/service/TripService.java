package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.UserRole;
import com.myprojects.splitbook.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    public String addNewTrip(String name,UserLogin user)
    {
        Trip trip = new Trip();
        trip.setName(name);
        trip.setOwnerId(user.getId());

        Member newMember = new Member();
        newMember.setName(user.getName());
        newMember.setOwner(true);
        newMember.setUserId(user.getId());
        newMember.setRole(UserRole.ADMIN);

        if(tripRepository.insertTrip(trip,newMember))
        {
            return "Your trip was added!";
        }
        return "Error adding trip!";
    }

    public List<Trip> getTripsByOwner(int ownerid)
    {

        List<Trip> trips = tripRepository.getTripsByOwnerId(ownerid);
        return trips;
    }

    public Trip getTripById(int id)
    {
        return tripRepository.getTripById(id);
    }

    public String addMemberToTrip(UserDto member, int tripId)
    {
        Trip trip = tripRepository.getTripById(tripId);
        List<Member> members = trip.getMembers();
        for(Member x : members)
        {
            if(x.getUserId()==member.getUid())
            {
                return "Member already added";
            }
        }
        Member newMember = new Member();
        newMember.setName(member.getName());
        newMember.setUserId(member.getUid());
        newMember.setOwner(false);
        newMember.setRole(UserRole.USER);
        tripRepository.insertMember(trip,newMember);

        return "Member added.";
    }

}
