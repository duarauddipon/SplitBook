package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.service.LoginService;
import com.myprojects.splitbook.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    LoginService loginService;
    @Autowired
    TripService tripService;

    @GetMapping("/addnewtrip")
    public String displayAddTripPage(Authentication authentication, Model model)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());

        model.addAttribute("user",user);
        return "addtrip";
    }

    @PostMapping("/addtripprocess")
    public String addNewTrip(Authentication authentication, Model model, @RequestParam String tripname)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());

        String res = tripService.addNewTrip(tripname,user.getId());
        model.addAttribute("msg",res);
        model.addAttribute("user",user);
        return "addtrip";
    }
}
