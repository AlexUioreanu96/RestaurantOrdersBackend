package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{cartId}/add/{productId}")
    public ResponseEntity<CartItem> addToCart(@PathVariable Long cartId, @PathVariable Long productId) throws Exception {
        CartItem cartItem = cartService.addToCart(cartId, productId);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long cartItemId, @RequestParam int quantity) throws Exception {
        cartService.updateCartItem(cartItemId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId, @RequestParam Long userId) throws Exception {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) throws Exception {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
