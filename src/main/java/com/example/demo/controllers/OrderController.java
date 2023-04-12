package com.example.demo.controllers;

import com.example.demo.models.Order;
import com.example.demo.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrdersService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws Exception {
        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) throws Exception {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Order> findOrderByUserId(@PathVariable Long userId)   {
        return new ResponseEntity<>(orderService.findOrderByUserId(userId), HttpStatus.OK);
    }
    @GetMapping("/all/user/{userId}")
    public ResponseEntity<List<Order>> findAllByUserId(@PathVariable Long userId)  {
        return new ResponseEntity<>(orderService.findAllByUserId(userId), HttpStatus.OK);
    }
}
