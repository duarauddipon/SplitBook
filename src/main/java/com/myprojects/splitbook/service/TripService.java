package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.dao.UserLoginRepository;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    public String addNewTrip(String name,int ownerid)
    {
        Trip trip = new Trip();
        trip.setName(name);
        trip.setOwnerId(ownerid);

        if(tripRepository.insertTrip(trip)!=null)
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
}
