package com.myprojects.splitbook.service;

import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BusinessUtils {

    @Autowired
    TripService tripService;
    @Autowired
    UserFriendService userFriendService;

    private static final String FRIEND_REQUEST_SUFFIX = " wants to be your friend.";

    public static List<String> friendRequestsStringParser(List<String> users)
    {
        List<String> res = users.stream().map(str -> str+FRIEND_REQUEST_SUFFIX).toList();
        return res;
    }

    public void initAttributes(UserLogin user, Model model)
    {
        model.addAttribute("username",user.getName());
        List<Trip> trips = tripService.getTripsByOwner(user.getId());
        if(trips==null)
        {
            model.addAttribute("notrip",'1');
        }
        else
        {
            model.addAttribute("notrip",'0');
        }
        model.addAttribute("mytrips",trips);
        int requestCount = userFriendService.getFriendRequests(user.getId()).size();
        model.addAttribute("requestcount",requestCount);
    }

    public UserDto UserDtoMapper(UserLogin user)
    {
        return new UserDto(user.getId(), user.getName(), user.getUsername());
    }
}
