package com.example.demo.controllers;

import com.example.demo.models.Order;
import com.example.demo.models.User;
import com.example.demo.models.payload.request.LoginRequest;
import com.example.demo.models.payload.request.SignupRequest;
import com.example.demo.models.payload.response.MessageResponse;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(loginRequest.getUsername());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = User.builder().username(signUpRequest.getUsername()).email(signUpRequest.getEmail()).password(signUpRequest.getPassword()).build();
        List<Order> orders = new ArrayList<>();
        Order order1 = Order.builder().user1(user).description("French fries with eqqs cost:10$").build();
        Order order2 = Order.builder().user1(user).description("Spaghetti with meat cost:20$").build();
        Order order3 = Order.builder().user1(user).description("Argentinean Beef with fries cost:100$").build();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        user.setOrders(orders);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok()
                .body(new MessageResponse("You've been signed out!"));
    }
}
