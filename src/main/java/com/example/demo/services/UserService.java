package com.example.demo.services;



import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.models.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;


    public Boolean userExists(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return true;
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return true;
        }
        return false;
    }

    public User registerUser(User user) throws Exception {
        if (userExists(user)) {
            throw new Exception("User already registered");
        }

        String userEmail = user.getEmail();
        String userUserName = user.getUsername();
        String userPassword = user.getPassword();

        boolean invalidUsername = (userUserName == null);
        boolean invalidEmail = (userEmail == null);
        boolean invalidPassword = (userPassword == null);

        if (invalidPassword) throw new Exception("Incorrect password");
        if (invalidUsername) throw new Exception("Incorrect username");
        if (invalidEmail) throw new Exception("Incorrect email");

        user.setEmail(userEmail);
        user.setUsername(userUserName);
        user.setPassword(passwordEncoder.encode(userPassword));
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        userRepository.flush();

        return savedUser;
    }

    public User findUserById(Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    }
    public User findUserByUsername(String username) throws Exception {
        if(username == null || username.equals("")) {
            throw new Exception("No username provided");
        }
        return userRepository.findByUsername(username);
    }

    public User updateUser(Long userId, User user) throws Exception {
        if(userId < 1 && user == null) {
            throw new Exception("Invalid arguments");
        }
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());

        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long userId) throws Exception {
        User deletedUser = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        userRepository.delete(deletedUser);
    }

    public List<User> findAllUsers()  {
        return userRepository.findAll();
    }
}
