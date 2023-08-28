package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.service.LoginService;
import com.myprojects.splitbook.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    TripService tripService;

    @GetMapping("/index")
    public String showWelcomePage()
    {
        return "index";
    }

    @GetMapping("/login")
    public String displayLoginPage()
    {
        return "loginpage";
    }

    @GetMapping("/signup")
    public String displaySignUpPage()
    {
        return "signuppage";
    }

    @PostMapping("/signupprocess")
    public String doSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password, ModelMap model)
    {
        UserLogin user = loginService.generateUser(email,password,name);
        String res = loginService.registerUser(user);
        model.addAttribute("msg",res);
        return "signuppage";
    }

    @GetMapping("/welcome")
    public String displayHomepage(Authentication authentication, Model model)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());
        List<Trip> trips = tripService.getTripsByOwner(user.getId());

        if(trips==null)
        {
            model.addAttribute("notrip",'1');
        }
        else
        {
            model.addAttribute("notrip",'0');
        }
        model.addAttribute("user",user);
        model.addAttribute("mytrips",trips);
        return "homepage";
    }
}
