package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.UserLoginRepository;
import com.myprojects.splitbook.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserLoginRepository userLoginRepository;

    public String registerUser(UserLogin user)
    {
        if(userLoginRepository.getUserByEmail(user.getEmail())!=null)       //email already registered
        {
            return "Email already registered!";
        }
        UserLogin userLogin = userLoginRepository.insertUser(user);
        if(userLogin==null)
        {
            return "Error occurred while registering!";
        }
        return "User successfully registered. You can login now.";
    }

    public boolean validateUser(String email, String password)
    {
        UserLogin user = userLoginRepository.getUserByEmail(email);
        if(user==null)
        {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
