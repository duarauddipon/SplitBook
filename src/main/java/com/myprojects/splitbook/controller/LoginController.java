package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/loginprocess")
    public String doLogin(@RequestParam String email, @RequestParam String password, ModelMap model)
    {
        boolean flag = loginService.validateUser(email,password);
        if(!flag)
        {
            model.put("message","Wrong email/credentials!");
            return "loginpage";
        }
        return "homepage";
    }

    @GetMapping("/signup")
    public String displaySignUpPage()
    {
        return "signuppage";
    }

    @PostMapping("/signupprocess")
    public String doSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password, ModelMap model)
    {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(email.toLowerCase());    //to avoid duplicates
        userLogin.setName(name);
        userLogin.setPassword(password);

        String res = loginService.registerUser(userLogin);
        model.addAttribute("message",res);
        return "signuppage";
    }
}
