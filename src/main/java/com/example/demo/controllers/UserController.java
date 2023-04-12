package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public User registerUser(@RequestBody User user) throws Exception {
        return userService.registerUser(user);
    }
    @PutMapping("/user/{userId}/update")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) throws Exception {
        return userService.updateUser(userId, user);
    }
    @DeleteMapping("/user/{userId}/delete")
    public void deleteUser(@PathVariable Long userId) throws Exception {
        userService.deleteUser(userId);
    }
    @GetMapping("/user/{username}")
    public User findUserByUsername(@PathVariable("username") String username) throws Exception {
        return userService.findUserByUsername(username);
    }
    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user/id/{userId}")
    public User findUserById(@PathVariable Long userId) throws Exception {
        return userService.findUserById(userId);
    }
}
