package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

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
        model.addAttribute("username",authentication.getName());
        return "homepage";
    }
}
