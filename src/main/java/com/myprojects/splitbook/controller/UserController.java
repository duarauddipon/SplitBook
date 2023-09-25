package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.RecordsDto;
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
import org.springframework.web.bind.annotation.*;

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
        List<Member> members = tripService.getTripMembers(trip.getId());
        model.addAttribute("mymembers",members);
        model.addAttribute("contribution",new Contribution());
        List<RecordsDto> records = utils.parseTripContributions(trip);
        model.addAttribute("records",records);
        model.addAttribute("memberscount",members.size());
        model.addAttribute("totalexpense",tripService.getTotalExpense(id));
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
        UserDto userDto = utils.userDtoMapper(userLogin);
        String res = tripService.addMemberToTrip(userDto,tripid);

        model.addAttribute("msg1",res);
        model.addAttribute("trip",trip);
        List<Member> members = tripService.getTripMembers(trip.getId());
        model.addAttribute("mymembers",members);
        model.addAttribute("contribution",new Contribution());
        List<RecordsDto> records = utils.parseTripContributions(trip);
        model.addAttribute("records",records);
        model.addAttribute("memberscount",members.size());
        model.addAttribute("totalexpense",tripService.getTotalExpense(tripid));
        return "dashboard";
    }

    @PostMapping("/addcontribution")
    public String doAddContribution(@ModelAttribute Contribution contribution, @RequestParam int tripid,
                                    @RequestParam(value = "beneficiaries") int[] beneficiaries)
    {
        Trip trip = tripService.getTripById(tripid);
        if(beneficiaries==null||beneficiaries.length==0)
        {
            return "redirect:/mytrip/dashboard/"+tripid;
        }
        contribution.setTrip(trip);
        trip.addContribution(contribution);
        List<Member> memberBeneficiaries = utils.memberListMapper(beneficiaries);
        tripService.addContribution(contribution,memberBeneficiaries);
        return "redirect:/mytrip/dashboard/"+tripid;
    }

    @GetMapping("/deleterecord/{tripid}/{rid}")             //TODO : Use @preauthorize to check authorization for deletion
    public String doDeleteContributionRecord(@PathVariable int tripid, @PathVariable int rid)
    {
        tripService.deleteContribution(rid);
        return "redirect:/mytrip/dashboard/"+tripid;
    }
}
