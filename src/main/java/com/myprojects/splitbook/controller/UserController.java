package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.UserDto;
import com.myprojects.splitbook.exception.ForbiddenException;
import com.myprojects.splitbook.service.BusinessUtils;
import com.myprojects.splitbook.service.LoginService;
import com.myprojects.splitbook.service.TripService;
import com.myprojects.splitbook.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    LoginService loginService;
    @Autowired
    TripService tripService;
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    BusinessUtils utils;

    @PostMapping("/addtripprocess")
    public String addNewTrip(Authentication authentication, Model model, @RequestParam String tripname)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());

        tripService.addNewTrip(tripname,user);
        //model.addAttribute("msg1",res);

        //utils.initAttributes(user,model);

        return "redirect:/welcome";
    }

    @GetMapping("/mytrip/dashboard/{id}")
    public String displayDashboard(Authentication authentication,@PathVariable int id, Model model)
    {
        Trip trip = tripService.getTripById(id);
        UserLogin user = loginService.getUserByUsername(authentication.getName());
        if(trip.getOwnerId()!=user.getId())     //unauthorized if user tries to get other's trips
        {
            throw new ForbiddenException();
        }
        model.addAttribute("trip",trip);
        return "dashboard";
    }

    @GetMapping("/addfriend")
    public String doAddFriend(Authentication authentication,@RequestParam String username,Model model)
    {
        UserLogin user1 = loginService.getUserByUsername(authentication.getName());

        UserLogin user2 = loginService.getUserByUsername(username);
        String res = userFriendService.sendFriendRequest(user1.getId(),user2.getId());
        model.addAttribute("msg2",res);

        utils.initAttributes(user1,model);

        return "homepage";
    }

    @GetMapping("/deleterequest")
    public String deleteRequestCall(@RequestParam int query, Authentication authentication, Model model)
    {
        UserLogin user1 = loginService.getUserByUsername(authentication.getName());

        UserLogin user2 = loginService.getUserByUsername(authentication.getName());
        String msg = userFriendService.declineFriendRequest(query,user2.getId());
        model.addAttribute("msg3",msg);

        utils.initAttributes(user1,model);

        return "redirect:/welcome";
    }

    @GetMapping("/acceptrequest")
    public String acceptRequestCall(@RequestParam int query, Authentication authentication, Model model)
    {
        UserLogin user1 = loginService.getUserByUsername(authentication.getName());

        UserLogin user2 = loginService.getUserByUsername(authentication.getName());
        String msg = userFriendService.acceptFriendRequest(query,user2.getId());
        model.addAttribute("msg3",msg);

        utils.initAttributes(user1,model);

        return "redirect:/welcome";
    }

    @GetMapping("/addmember")
    public String doAddMember(@RequestParam String username, @RequestParam int tripid, Authentication authentication, Model model)
    {
        UserLogin userLogin = loginService.getUserByUsername(username);
        Trip trip = tripService.getTripById(tripid);
        UserDto userDto = utils.UserDtoMapper(userLogin);
        String res = tripService.addMemberToTrip(userDto,tripid);

        model.addAttribute("msg1",res);
        model.addAttribute("trip",trip);
        return "dashboard";
    }
}
