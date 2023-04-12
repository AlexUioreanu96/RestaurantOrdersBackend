package com.example.demo.controllers;

import com.example.demo.UserLoginDto;
import com.example.demo.models.User;
import com.example.demo.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserAuthenticationService userService;


    @GetMapping("/login")
    public UserLoginDto authentication() throws Exception {
        User user = userService.authenticate();
        if(user.getUsername() != null && user.getPassword() != null){
            return new UserLoginDto(user.getId(),user.getUsername(), user.getPassword());
        } else {
            throw new RuntimeException("User invalid credentials");
        }
    }

}


