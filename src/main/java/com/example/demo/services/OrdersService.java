package com.example.demo.services;



import com.example.demo.models.*;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public Order saveOrder(Order order) throws Exception {
        User loggedInUser = userAuthenticationService.getLoggedInUser();
        if (loggedInUser == null) {
            throw new Exception("User cannot be null");
        }
        order.setUser(loggedInUser);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setStreetName(order.getStreetName());
        order.setStreetNumber(order.getStreetNumber());

        int totalPrice = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            Product fetchedProduct = productService.findProductById(orderItem.getProduct().getId());
            orderItem.setProduct(fetchedProduct);
            orderItem.setOrder(order);
            Integer productPrice = orderItem.getProduct().getPrice();

            if (productPrice == null) {
                throw new Exception("Product price cannot be null");
            }
            int itemTotalPrice = productPrice * orderItem.getQuantity();
            totalPrice += itemTotalPrice;
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) throws Exception {
        Order deletedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
        orderRepository.delete(deletedOrder);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    public Order findOrderByUserId(Long userId)   {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> findAllByUserId(Long userId)  {
        return orderRepository.findAllByUserId(userId);
    }

}
