package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.UserLoginRepository;
import com.myprojects.splitbook.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    public String registerUser(UserLogin user)
    {
        if(userLoginRepository.getUserByUsername(user.getUsername())!=null)       //username already registered
        {
            return "Email already registered!";
        }
        UserLogin userLogin = userLoginRepository.insertUser(user);
        if(userLogin==null)
        {
            return "Error occurred while registering!";
        }
        return "User successfully registered. Your username is "+userLogin.getUsername();
    }

    public UserLogin generateUser(String email, String password, String name)
    {
        Random random = new Random();

        UserLogin newUser = new UserLogin();
        newUser.setName(name);
        newUser.setEmail(email.toLowerCase());                  //to avoid duplicates
        newUser.setPassword(password);
        newUser.setUsername(name.toLowerCase()+random.nextInt(1000));  //generating a random unique username
        newUser.setRole("USER");        //TODO : hardcoding for now;add other roles

        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin user = userLoginRepository.getUserByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
